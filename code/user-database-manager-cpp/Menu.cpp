#include "Menu.h"

#include <iostream>

void displayMenu()
{
    std::cout << '\n'
              << "----- MENU -----" << '\n'
              << LIST   << ": List all users" << '\n'
              << ADD    << ": Add a user" << '\n'
              << REMOVE << ": Remove a user" << '\n'
              << SORT   << ": Sort the list" << '\n'
              << QUIT   << ": Quit" << '\n' << '\n';
}

// get input from the user and do some data validation it first
int getSelection(const std::string& prompt, const int& high, const int& low)
{
    int input {0};
    bool validSelection {false};

    do
    {
        std::cout << prompt << ": ";
        if (std::cin >> input)
        {
            if (input >= low && input <= high)
            {
                validSelection = true;
            }
            else
            {
                std::cout << "\nNumber not within range: [" << low << ", " << high << "]" << '\n';
            }
        }
        else
        {
            std::cin.clear();
            std::cin.ignore(100, '\n');
            std::cout << "\nInput is not an integer." << '\n';
        }
    }
    while (!validSelection);

    std::cout << '\n';

    return input;
}
