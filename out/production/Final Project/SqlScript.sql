DROP DATABASE IF EXISTS `richtek_computers`;
CREATE DATABASE IF NOT EXISTS `richtek_computers`;
SHOW DATABASES ;
USE `richtek_computers`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
    UserId VARCHAR(15),
    Name VARCHAR(45),
    UserName VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    Position varchar(45),
    Password varchar(45),
    Address TEXT,
    Contact VARCHAR(15),
    Salary DOUBLE DEFAULT 0.00,

    CONSTRAINT PRIMARY KEY (UserId)
    );
SHOW TABLES ;
DESCRIBE `user`;

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer`(
    CustId VARCHAR(6),
    CustName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    CustAddress VARCHAR(30),
    Contact VARCHAR(20),
    CONSTRAINT PRIMARY KEY (CustId)
    );
SHOW TABLES ;
DESCRIBE `customer`;

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item`
(
    ItemId VARCHAR(6),
    Name TEXT,
    Brand VARCHAR(15) NOT NULL,
    Series VARCHAR(30),
    ModelId VARCHAR(15),
    Type VARCHAR(15),
    MoreDetail TEXT,
    Availability VARCHAR(25),
    QtyONHand int(3),
    Price DECIMAL (6) DEFAULT 0.00,
    ImageAddress VARCHAR(30),
    CONSTRAINT PRIMARY KEY (ItemId)
);
SHOW TABLES ;
DESCRIBE `item`;

DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order`(
    OrderId VARCHAR(15),
    CustId VARCHAR(15),
    OrderDate DATE,
    OrderTime VARCHAR(15),
    Cost DECIMAL(6,2),
    Discount double,
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (CustId) REFERENCES `customer`(CustId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `order`;

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE IF NOT EXISTS `order_detail`(
    ItemCode VARCHAR(15),
    OrderId VARCHAR(15),
    Qty INT(3),
    Price DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (ItemCode, OrderId),
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES `item`(ItemId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (OrderId) REFERENCES `order`(OrderId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `order_detail`;

DROP TABLE IF EXISTS `dealer`;
CREATE TABLE IF NOT EXISTS `dealer`(
    DealerId VARCHAR (15),
    Name VARCHAR (30),
    Brand VARCHAR(15),
    Address VARCHAR(45),
    Contact VARCHAR(15),
    CONSTRAINT PRIMARY KEY (DealerId)
);
SHOW TABLES ;
DESC `dealer`;

DROP TABLE IF EXISTS `supply_request`;
CREATE TABLE IF NOT EXISTS `supply_request`(
    SupplyId VARCHAR (15),
    DealerId VARCHAR (15),
    Brand VARCHAR(15),
    Date DATE,
    Time VARCHAR(15),
    Status VARCHAR(10),
    DateOfExpect DATE,
    TotalCost DECIMAL (7,2),
    CONSTRAINT PRIMARY KEY (SupplyId),
    CONSTRAINT FOREIGN KEY (DealerId) REFERENCES dealer(dealerid) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESC `supply_request`;

DROP TABLE IF EXISTS `supply_detail`;
CREATE TABLE IF NOT EXISTS `supply_detail`(
    SupplyId VARCHAR(10),
    ItemId VARCHAR(10),
    Qty INT(3),
    Price DECIMAL(6,2),
    CONSTRAINT FOREIGN KEY (SupplyId) REFERENCES supply_request(SupplyId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (ItemId) REFERENCES `item`(ItemId) ON DELETE  CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESC `supply_detail`;


Insert into `item` VALUES('L-003','ASUS','ROG Strix SCAR 15','G532-G532LWS','Intel Core i9-10980HK GeForce RTX 2070 SUPER ','Gaming','
Intel® Core™ i9-10980HK Processor 2.4 GHz (16M Cache, up to 5.3 GHz, 8 cores
NVIDIA® GeForce® RTX 2070 SUPER™ 8GB GDDR6
Storage upgrades
15.6-inch
FHD (1920 x 1080) 16:9
anti-glare display
sRGB:100%
Adobe:75.35%
Refresh Rate:300Hz
Response Time:3ms
IPS-level
16GB DDR4 SO-DIMM(3200MHz for i7-10870H/i7-10875H/i9-10980HK and 2933MHz for i5-10300H/i7-10750H) x 2
Max Capacity : 32GB
1TB + 1TB M.2 NVMe™ PCIe® 3.0 RAID0 SSD

OSCOO ON900 128GB NVME M.2 SSD 8000
Addlink S70 256GB SSD NVMe PCIe Gen3x4 M.2 11000
Addlink S68 512GB SSD NVMe PCIe Gen3x4 M.2 18000
Addlink S72 1.0TB SSD NVMe PCIe Gen3x4 29500
Addlink S72 2.0TB SSD NVMe PCIe Gen3x4 62500
Samsung 980 NVMe M.2 SSD 250GB 18000
Samsung 980 NVMe M.2 SSD 500GB 24500
Samsung 980 NVMe M.2 SSD 1TB 42500
SAMSUNG 980 PRO Gen.4 1TB NVMe M.2 SSD (3Y) 59500
SAMSUNG 980 PRO Gen.4 500GB NVMe M.2 SSD (3Y) 37000','In stock',3,385000,'/assets/src/L_scar_15.png');



use `richtek_computers`;
Update `item` set `QtyOnHand`='20' where `ItemId`='I-001';
Update `item` set `QtyOnHand`='20' where `ItemId`='I-002';
Update `item` set `QtyOnHand`='20' where `ItemId`='I-003';
Update `item` set `QtyOnHand`='20' where `ItemId`='I-004';
Update `item` set `QtyOnHand`='20' where `ItemId`='I-007';
Update `item` set `QtyOnHand`='50' where `ItemId`='I-008';
Update `item` set `QtyOnHand`='50' where `ItemId`='I-009';
Update `item` set `QtyOnHand`='50' where `ItemId`='I-010';
Update `item` set `Availability`='In Stock' where `ItemId`='I-001';
Update `item` set `Availability`='In Stock' where `ItemId`='I-002';
Update `item` set `Availability`='In Stock' where `ItemId`='I-003';
Update `item` set `Availability`='In Stock' where `ItemId`='I-004';
Update `item` set `Availability`='In Stock' where `ItemId`='I-007';
Update `item` set `Availability`='In Stock' where `ItemId`='I-008';
Update `item` set `Availability`='In Stock' where `ItemId`='I-009';
Update `item` set `Availability`='In Stock' where `ItemId`='I-010';