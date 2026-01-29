#include "User.h"

namespace nsa {

    std::size_t s_idCounter {0};

    User::User(const std::string& name,
            const std::string& dob,
            const std::string& birthplace)
        : name{name}, dob{dob}, birthplace{birthplace}, id{++s_idCounter}
    { }

    User::~User()
    {

    }

    std::string User::getName() const
    {
        return name;
    }

    std::string User::getDOB() const
    {
        return dob;
    }

    std::string User::getBirthplace() const
    {
        return birthplace;
    }

    std::size_t User::getId() const
    {
        return id;
    }

    void User::setName(const std::string& name)
    {
        this->name = name;
    }

    void User::setDOB(const std::string& dob)
    {
        this->dob = dob;
    }

    void User::setBirthplace(const std::string& birthplace)
    {
        this->birthplace = birthplace;
    }

};
