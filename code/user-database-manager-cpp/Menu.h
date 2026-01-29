#ifndef MENU_H_
#define MENU_H_

#include "Menu.h"

#include <string>

// an enum to make our menu logic easier to read
enum MenuOption
{
    QUIT, LIST, ADD, REMOVE, SORT,
};

// prototypes for our menu functions
void displayMenu();
int getSelection(const std::string& prompt, const int& high=10, const int& low=0);


#endif // MENU_H_
