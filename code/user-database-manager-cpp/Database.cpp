#include "Database.h"

#include <algorithm>
// #include <iterator>

namespace nsa {

    Database::Database(const std::string& filename)
        : filename{filename}
    {
        fetchUsers();
    }

    Database::~Database()
    {
        writeDatabase();
    }

    std::string Database::getFilename() const
    {
        return filename;
    }

    std::vector<User> Database::getUsers() const
    {
        return users;
    }

    void Database::addUser(const User& user)
    {
        users.push_back(user);
    }

    bool Database::removeUser(const std::size_t& id)
    {
        auto erased = std::erase_if(users, [&](const User& user) {
            return user.getId() == id;
        });

        // users.erase(it);
        if (erased > 0)
            return true;

        return false;
    }

    void Database::setFilename(const std::string& filename)
    {
        this->filename = filename;
    }

    void Database::sort()
    {
        std::sort(users.begin(), users.end());
    }

    void Database::save() const
    {
        writeDatabase();
    }

    std::size_t Database::getSize() const
    {
        return users.size();
    }

    void Database::fetchUsers()
    {
        file.open(filename, std::ios::in);

        std::string line;
        while (!file.eof())
        {
            std::getline(file, line);
            if (line != "")
                users.push_back(parseLineToUser(line));
        }

        file.close();
    }

    void Database::writeDatabase() const
    {
        file.open(filename, std::ios::out);

        for (User user : users)
        {
            file << user.getName() << ", "
                 << user.getDOB() << ", "
                 << user.getBirthplace() << ',' << '\n';
        }

        file.close();
    }

    User Database::parseLineToUser(const std::string& line)
    {
        std::string name,
                    dob,
                    birthplace;
        std::size_t start_i,
                    nextComma_i;

        start_i = 0;
        nextComma_i = line.find(',');
        name = line.substr(start_i, nextComma_i-start_i);

        start_i = nextComma_i + 2;
        nextComma_i = line.find(',', start_i);
        dob = line.substr(start_i, nextComma_i-start_i);

        start_i = nextComma_i + 2;
        nextComma_i = line.find(',', start_i);
        birthplace = line.substr(start_i, nextComma_i-start_i);

        return User(name, dob, birthplace);
    }

};
