// Calculate the sale price of a product based on production cost and target profit percentage.

#include <iostream>

int main() {
    double productionCostPerDrive{8.00}; // in dollars
    double profitPercentage{0.35}; // percentage, where 1.00 == 100%

    double sellPrice = (profitPercentage * productionCostPerDrive) + productionCostPerDrive;

    std::cout << "To have a 35% profit, the flash drive should sell for $" << sellPrice;

    return 0;
}
