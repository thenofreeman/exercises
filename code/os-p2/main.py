import threading
import queue
import random
import time

MS = 1/1000

def log_builder(subject_type, subject_id, object_type):
    def log_fn(message, object_id=None):
        if object_id is not None:
            print(f"{subject_type} {subject_id} [{object_type} {object_id}]: {message}")
        else:
            print(f"{subject_type} {subject_id} []: {message}")

    return log_fn

class Bank:
    def __init__(self):
        self.n_tellers = 3
        self.n_customers = 50
        
        self.door = threading.Semaphore(2)
        self.safe = threading.Semaphore(2)
        self.manager = threading.Semaphore(1)

        self.tellers = queue.Queue(self.n_tellers)
        
        self.open_barrier = threading.Barrier(self.n_tellers)
        
    def simulate(self):
        teller_threads = []
        customer_threads = []

        for i in range(self.n_tellers):
            t = Teller(i, self)
            t.start()
            teller_threads.append(t)

        for i in range(self.n_customers):
            c = Customer(i, self)
            c.start()
            customer_threads.append(c)
            
        for c in customer_threads:
            c.join()

        for t in teller_threads:
            t.customer_id.put(None)
            t.serving_customer.release()

        for t in teller_threads:
            t.join()

        self.close()
    
    def close(self):
        print("The bank closes for the day.")

class Teller(threading.Thread):
    def __init__(self, teller_id, bank):
        super().__init__()

        self.id = teller_id
        self.bank = bank

        self.serving_customer = threading.Semaphore(0)
        self.requested_transaction = threading.Semaphore(0)
        self.received_transaction = threading.Semaphore(0)
        self.completed_transaction = threading.Semaphore(0)
        self.customer_leaving = threading.Semaphore(0)

        self.customer_id = queue.Queue(maxsize=1)
        self.transaction_type = queue.Queue(maxsize=1)
        
        self.log = log_builder("Teller", teller_id, "Customer")
        
    def run(self):
        self.bank.open_barrier.wait()
        
        while True:
            self.bank.tellers.put(self)
            self.log("ready to serve.")
            
            self.log("waiting for a customer.")
            self.serving_customer.acquire()
            customer_id = self.customer_id.get()

            if customer_id is None:
                self.log("leaving for the day.")
                break

            self.log("serving a customer.", customer_id)
            
            self.log("asks for transaction.", customer_id)
            self.requested_transaction.release()
            
            self.received_transaction.acquire()
            transaction_type = self.transaction_type.get()
            self.log(f"handling {transaction_type} transaction.", customer_id)
            
            if transaction_type == 'withdrawal':
                self.log("going to the manager.", customer_id)
                with self.bank.manager:
                    self.log("getting manager's permission.", customer_id)
                    time.sleep(random.uniform(5*MS, 30*MS))

                self.log("got manager's permission.", customer_id)
                
            self.log("going to safe.", customer_id)
            with self.bank.safe:
                self.log("entering safe.", customer_id)
                time.sleep(random.uniform(10*MS, 50*MS))
                self.log("leaving safe.", customer_id)
                
            self.log(f"finishes {transaction_type} transaction.", customer_id)
            self.completed_transaction.release()
            
            self.log("wait for customer to leave.", customer_id)
            self.customer_leaving.acquire()

class Customer(threading.Thread):
    def __init__(self, customer_id, bank):
        super().__init__()
        
        self.id = customer_id
        self.bank = bank
        
        self.log = log_builder("Customer", customer_id, "Teller")
    
    def run(self):
        transaction_type = random.choice(["deposit", "withdrawal"])
        self.log(f"wants to perform a {transaction_type} transaction.")
        
        time.sleep(random.uniform(0, 100*MS))
        
        self.log("going to bank.")
        with self.bank.door:
            self.log("entering bank.")

            self.log("getting in line.")
            teller = self.bank.tellers.get()
            self.log("selecting a teller.")

            teller_id = teller.id
            self.log("selects teller.", teller_id)
            
            self.log("introduces itself.", teller_id)
            teller.customer_id.put(self.id)
            teller.serving_customer.release()
            
            teller.requested_transaction.acquire()
            
            self.log(f"asks for {transaction_type} transaction.", teller_id)
            teller.transaction_type.put(transaction_type)
            teller.received_transaction.release()
            
            teller.completed_transaction.acquire()
            self.log("goes to door.")

            teller.customer_leaving.release()
            self.log("leaves the bank.")

def main():
    bank = Bank()
    bank.simulate()

if __name__ == '__main__':
    main()