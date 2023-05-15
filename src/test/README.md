# Test Area

Normally I was leaning towards using cucumber for @Tests, but then I gave up. From this point on, I'll add it to the comment lines in case anyone wants to use the project for a different purpose.


## Test Scenarios

#### addBackpackAndBikeLightToCartToCart

    Feature Add products to cart
        As a user, I should be able to add products to the cart 

    Scenario Add clothes and shoes to cart
        Given User is logged in to the application
        And it's on the Home page
        When the user selects the product "Backpack"
        And clicks on the "Add to Cart" button
        And the user selects the product "Bike Light"
        And clicks on the "Add to Cart" button
        And goes to the Cart and checks that the products have been added
        Then the cart contains "Backpack" and "Bike Light" products

  