#ifndef DATABASE_H_
#define DATABASE_H_

#include "Database.h"

#include <fstream>
#include <string>
#include <vector>

#include "User.h"

namespace nsa {

    class Database
    {
        public:
            Database() = delete;
            Database(const std::string& filename);

            virtual ~Database();

            std::string getFilename() const;
            std::vector<User> getUsers() const;

            void addUser(const User& user);
            bool removeUser(const std::size_t& id);

            void setFilename(const std::string& filename);

            void sort();
            void save() const;
            std::size_t getSize() const;

        private:
            std::string filename;
            mutable std::fstream file;

            std::vector<User> users;

            void fetchUsers();
            void writeDatabase() const;
            User parseLineToUser(const std::string& line);
    };

};

#endif // DATABASE_H_
