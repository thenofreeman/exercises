#include <iostream>

#include "Database.h"
#include "Menu.h"
#include "User.h"

int main()
{
    nsa::Database db ("data.txt");

    bool running = true;
    while (running)
    {
        displayMenu();

        switch(getSelection("Make a selection", 4))
        {
            case LIST:
                for (nsa::User user : db.getUsers())
                    std::cout << user << std::endl;
                break;
            case ADD:
                // db.addUser()
            case REMOVE:
            {
                std::size_t id = getSelection("Enter an ID to delete");
                if (db.removeUser(id))
                    std::cout << "User with ID " << id << " removed." << '\n';
                else
                    std::cout << "User with ID " << id << " not found." << '\n';
                break;
            }
            case SORT:
                db.sort();
                break;
            case QUIT:
                running = false;
                break;
            default:
                std::cout << "Not a valid menu option. Try again." << '\n';
                break;
        }
    }

    // db.save();

    return 0;
}
