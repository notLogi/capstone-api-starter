## Video Game Store API
This API utilizes REST storing persistent data that involves the store's products, order summary, and the user's shopping cart.
Owners using this API can use this to configure their database for categories and their available products. 


## User stories
- As an administrator of the database, I want to be able to update, delete, and insert a category to show what game categories there are
- As a user, I want to be able to get the products based on the information given, so I can actually see what is available.
- As a user, I want to be able to see the latest product list, so I don't see any outdated/duplicate products that can cause me confusion
- As a user, I want to add items to my cart, so I can check out items that I want to buy
- As a user, I want to clear my cart so I can restart my list of what I want to buy.
- As a user, I want to update the quantity of the product that I am buying, so I don't need to spam click + or -.
- As a user, I want to update my profile details so it reflects my identity
- As a user, I want to checkout my items, so I can purchase my items
## Some features include:

**Read data from the database**
- Persistent data being stored in a database using MySQL.

**Changing profile details**
- Users can change their profile details to ensure what they order can be delivered to the right address

**Add and removing products from the shopping cart**
- Users can remove and add as many products as they want inside their shopping cart, which will get stored in the database.

**Filtering products**
- Users can filter out products based on their minimum and maximum price and/or category.


## Setup


### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'EasyshopApplication.main()'' to start the application.
6. Have to download the website in a separate folder, and open up index.html
7. While running, you can configure 

## Technologies Used

- Java SDK 17
- IntelliJ IDEA
- MySQL Database
- Insomnia
- Spring Boot

## Demo
!  ![ezgif-8996a302de041b12](https://github.com/user-attachments/assets/596fe458-d778-4c4e-85a4-b22160344690)






## Interesting Part of my Code:
- A couple of parts:
  - Looping through a map, grabbing its product and id through the key and value
  - Using DUPLICATE KEY in my sql query, allowing no duplicates allowed if existed already and instead incrementing quantity by 1
  - Implementing a service that deals with 4 DAOs corresponding with the user's order.
  - Implementing two catches in my controller methods to display the correct HTTP status.


## Future versions:
- Some features I would like to improve on in the future:
  - Being able to remove a specific quantity of products in your shopping cart, instead of clearing the whole cart
  - Implement a search bar in the front-end so people can search their game of choice
  - Changing the max price bar in the front-end depending on the highest priced product
  - Having a checkout button

## Special thanks to:
- Raymond
- Tamir
- Stephen

## Contributors:
- Roger Su
