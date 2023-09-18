create DATABASE Test;
use Test;

CREATE TABLE Roles (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name ENUM('ADMIN','USER')
);


CREATE TABLE Users ( 
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   fullname VARCHAR(100) NOT NULL ,
   email VARCHAR(100) UNIQUE NOT NULL,
   phone_number VARCHAR(20),
   address VARCHAR(255),
   password VARCHAR(255) NOT NULL,
   avatar VARCHAR(255),
   role_id INT NOT NULL ,
   account_status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') ,
   FOREIGN KEY (role_id) REFERENCES Roles(id)
);

CREATE TABLE Categories (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE Products (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  category_id INT NOT NULL,
  name VARCHAR(100) NOT NULL ,
  price DECIMAL(20, 2) NOT NULL ,
  image VARCHAR(200) NOT NULL ,
  brand varchar(200) NOT NULL,
  buy_count INT NOT NULL default 0,
  description VARCHAR(200),
  quantity INT NOT NULL ,
  FOREIGN KEY (category_id) REFERENCES Categories(id)
);



CREATE TABLE Status (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED','CANCELLED','REJECTED') NOT NULL
);
CREATE TABLE Reasons (
	  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      description text
);


CREATE TABLE Orders (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id int,
  fullname VARCHAR(100), 
  phone_number VARCHAR(20) ,
  address VARCHAR(200),
  note VARCHAR(200),
  order_date DATE,
  status int,
  reason int,
  FOREIGN KEY (status) REFERENCES Status(id),
  FOREIGN KEY (reason) REFERENCES Reasons(id),
  FOREIGN KEY (user_id) REFERENCES Users(id)
);



CREATE TABLE Order_detail (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  price DECIMAL(20, 2),
  amount INT NOT NULL,
  total_money DECIMAL(20, 2),
  FOREIGN KEY (order_id) REFERENCES Orders(id),
  FOREIGN KEY (product_id) REFERENCES Products(id)
);

CREATE TABLE Carts (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id INT NOT NULL,
   FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Cart_detail (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   product_id INT NOT NULL,
   cart_id INT NOT NULL,
   amount INT NOT NULL,
   price DECIMAL(20, 2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (cart_id) REFERENCES Carts(id),
   FOREIGN KEY (product_id) REFERENCES Products(id)
);

CREATE TABLE Feedbacks (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL, 
  order_id INT NOT NULL,
  title VARCHAR(100),
  description VARCHAR(200),
  rating DECIMAL(2,1) NOT NULL,
  FOREIGN KEY (product_id) REFERENCES Products(id),
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);


