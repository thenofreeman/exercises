#ifndef USER_H_
#define USER_H_

#include "User.h"

#include <iostream>
#include <string>

namespace nsa {

    class User
    {
        public:
            User() = delete;
            User(const std::string& name,
                 const std::string& dob,
                 const std::string& birthplace);

            virtual ~User();

            std::string getName() const;
            std::string getDOB() const;
            std::string getBirthplace() const;
            std::size_t getId() const;

            void setName(const std::string& name);
            void setDOB(const std::string& dob);
            void setBirthplace(const std::string& birthplace);

            friend std::ostream& operator<<(std::ostream& os, const User& user)
            {
                os //<< "ID-" << user.id << ": "
                   << user.name << " (#" << user.id << ") was born in "
                   << user.birthplace << " on "
                   << user.dob << ".";

                return os;
            }

            bool operator>(const User& other) const
            {
                return (dob > other.dob);
            }

            bool operator<(const User& other) const
            {
                return (dob < other.dob);
            }

            bool operator==(const User& other) const
            {
                if (this->name != other.name) return false;
                if (this->dob != other.dob) return false;
                if (this->birthplace != other.birthplace) return false;

                return true;
            }

        private:
            std::string name;
            std::string dob;
            std::string birthplace;
            std::size_t id;

            inline static std::size_t s_idCounter;

    };
};

#endif // USER_H_
