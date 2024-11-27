CREATE DATABASE Airport;
USE Airport;
CREATE TABLE User (
	id INT PRIMARY KEY   ,
    name VARCHAR(70) ,
    password VARCHAR(70) ,
    email VARCHAR(70) , 
    type VARCHAR(70) ,
    profile_id INT 
);
CREATE TABLE AirportStaff (
	id INT PRIMARY KEY  , 
    name VARCHAR(70) ,
    phone VARCHAR(70) ,
    cncic VARCHAR(20) ,
    gender VARCHAR(1) ,
    login_id INT,
    FOREIGN KEY (login_id) REFERENCES User(id)
);
CREATE TABLE Queue (
	id INT PRIMARY KEY   ,
    type VARCHAR(10) ,
    priority_available TINYINT 
);
CREATE TABLE Terminal (
	id INT PRIMARY KEY  ,
    name VARCHAR(70),
    capacity INT ,
    security_checkpoints INT 
);
CREATE TABLE Notification (
	id INT PRIMARY KEY  ,
    level INT ,
    message VARCHAR(300),
    type VARCHAR(70)
);
ALTER TABLE Notification
ADD timestamp DATETIME;
CREATE TABLE Gate (
	id INT PRIMARY KEY  ,
    parent_terminal INT ,
    queue_id INT ,
    current_flight INT ,
    max_flight INT ,
    FOREIGN KEY (parent_terminal) REFERENCES Terminal(id) ,
    FOREIGN KEY (queue_id) REFERENCES Queue(id)
);
CREATE TABLE Lounge (
	id INT PRIMARY KEY  ,
    parent_terminal INT ,
    capacity INT ,
    access_fee FLOAT ,
    queue_id INT ,
	FOREIGN KEY (parent_terminal) REFERENCES Terminal(id) ,
    FOREIGN KEY (queue_id) REFERENCES Queue(id)
);
CREATE TABLE ServicesOffered (
	id INT PRIMARY KEY  ,
    description VARCHAR(200)
);
CREATE TABLE Lounge_Services (
	service_id INT ,
    lounge_id INT , 
    PRIMARY KEY (service_id , lounge_id) , 
    FOREIGN KEY (service_id) REFERENCES ServicesOffered(id) , 
    FOREIGN KEY (lounge_id) REFERENCES Lounge(id)
);
CREATE TABLE AdminNotification (
	notification_id INT ,
    receipient_id INT ,
    PRIMARY KEY (notification_id , receipient_id) ,
    FOREIGN KEY (notification_id) REFERENCES Notification(id) ,
    FOREIGN KEY (receipient_id) REFERENCES AirportStaff(id) 
);
CREATE TABLE Flight (
	id INT PRIMARY KEY  ,
    flight_number VARCHAR(20) , 
    gate_id INT ,
    departure_time DATETIME ,
    arrival_time DATETIME ,
    departure_city VARCHAR(30) ,
    arrival_city VARCHAR(30) , 
    FOREIGN KEY (gate_id) REFERENCES gate(id)
); 
CREATE TABLE Seat (
	id INT PRIMARY KEY ,
    seat_number VARCHAR(20) , 
    seat_type VARCHAR(30) ,
    is_booked TINYINT DEFAULT 0 ,
    flight_id INT ,
    FOREIGN KEY (flight_id) REFERENCES Flight(id)
); 
CREATE TABLE Payment (
	id INT PRIMARY KEY ,
    amount FLOAT ,
    transaction_date DATETIME ,
    is_pending TINYINT 
);
CREATE TABLE CheckIn (
	id INT PRIMARY KEY ,
    checkin_time DATETIME  ,
    description VARCHAR(100) ,
    is_checkedin TINYINT DEFAULT 0
);
CREATE TABLE TicketDescription (
	id INT PRIMARY KEY ,
    refundable TINYINT DEFAULT 0 ,
    flight_id INT ,
    class VARCHAR(30) , 
    price FLOAT ,
    baggage_allowance FLOAT ,
    FOREIGN KEY (flight_id) REFERENCES Flight(id)
);
CREATE TABLE Ticket (
	id INT PRIMARY KEY ,
    seat_id INT ,
    ticket_description INT ,
    type VARCHAR(30) , 
    payment_id INT ,
    FOREIGN KEY (seat_id) REFERENCES Seat(id) ,
    FOREIGN KEY (ticket_description) REFERENCES TicketDescription(id) ,
    FOREIGN KEY (payment_id) REFERENCES Payment(id)
);
CREATE TABLE BoardingPass (
	id INT PRIMARY KEY ,
    gate_id INT ,
    flight_id INT ,
    seat_num INT ,
    p_boarding TINYINT DEFAULT 0 ,
    FOREIGN KEY (gate_id) REFERENCES Gate(id) , 
    FOREIGN KEY (flight_id) REFERENCES Flight(id) ,
    FOREIGN KEY (seat_num) REFERENCES Seat(id)
);
CREATE TABLE passenger (
	id INT PRIMARY KEY  , 
    name VARCHAR(70) ,
    phone VARCHAR(70) ,
    cncic VARCHAR(20) ,
    gender VARCHAR(1) ,
    passport_num VARCHAR(30) ,
    login_id INT,
    pass_id INT ,
    ticket_id INT ,
    checkin_id INT ,
    FOREIGN KEY (login_id) REFERENCES User(id) , 
    FOREIGN KEY (pass_id) REFERENCES BoardingPass(id) , 
    FOREIGN KEY (ticket_id) REFERENCES Ticket(id) ,
    FOREIGN KEY (checkin_id) REFERENCES CheckIn(id)
);
CREATE TABLE PassengerNotification (
	notification_id INT ,
    receipient_id INT ,
    PRIMARY KEY (notification_id , receipient_id) ,
    FOREIGN KEY (notification_id) REFERENCES Notification(id) ,
    FOREIGN KEY (receipient_id) REFERENCES Passenger(id) 
);
CREATE TABLE Queue_Passenger (
	passenger_id INT ,
    queue_id INT ,
    waiting_status TINYINT DEFAULT 1 , 
    PRIMARY KEY (passenger_id , queue_id) ,
    FOREIGN KEY (passenger_id) REFERENCES Passenger(id) , 
    FOREIGN KEY (queue_id) REFERENCES Queue(id)
);
CREATE TABLE Baggage(
	id INT PRIMARY KEY ,
    weight FLOAT ,
    dimensions VARCHAR(20) , 
    location VARCHAR(40) ,
    passenger_id INT ,
    flight_id INT ,
    FOREIGN KEY (passenger_id) REFERENCES Passenger(id) , 
    FOREIGN KEY (flight_id) REFERENCES Flight(id)
);
CREATE TABLE Passenger_Flight (
	passenger_id INT ,
    flight_id INT ,
    PRIMARY KEY(passenger_id , flight_id) , 
	FOREIGN KEY (passenger_id) REFERENCES Passenger(id) , 
    FOREIGN KEY (flight_id) REFERENCES Flight(id)
);
CREATE TABLE EmergencySituation (
	id INT PRIMARY KEY , 
    description VARCHAR(200) , 
    emergency_time DATETIME ,
    isresolved TINYINT  DEFAULT 0 ,
    level INT ,
    authorities_notified TINYINT DEFAULT 0 
);
CREATE TABLE Authority (
	id INT PRIMARY KEY ,
    name VARCHAR(70) , 
    contact VARCHAR(20) , 
    response_time TIME, 
    type VARCHAR(30) ,
    emergency_id INT ,
    num_doctors INT DEFAULT 0 ,
    specialization VARCHAR(100) DEFAULT NULL ,
    num_trucks INT DEFAULT 0 ,
    fire_equipment TINYINT DEFAULT 1 ,
    team_size INT DEFAULT 0 ,
    weapons_available TINYINT DEFAULT 1
);
USE Airport;
-- Inserting data for Terminals
INSERT INTO Terminal (id, name, capacity, security_checkpoints)
VALUES 
(1, 'International Terminal 1', 5000, 20),
(2, 'Domestic Terminal 2', 3000, 15),
(3, 'VIP Terminal 3', 1000, 5),
(4, 'Cargo Terminal 4', 2000, 8),
(5, 'Private Terminal 5', 800, 3);

-- Inserting data for Queues (unique to each gate and lounge)
INSERT INTO Queue (id, type, priority_available)
VALUES 
(1, 'Gate', 0),
(2, 'Gate', 0),
(3, 'Gate', 0),
(4, 'Gate', 0),
(5, 'Gate', 0),
(6, 'Gate', 0),
(7, 'Gate', 0),
(8, 'Lounge', 1),
(9, 'Lounge', 1),
(10, 'Lounge', 1),
(11, 'Lounge', 1),
(12, 'Lounge', 1),
(13, 'Lounge', 1);

-- Inserting data for Gates with unique Queues
INSERT INTO Gate (id, parent_terminal, queue_id, current_flight, max_flight)
VALUES 
(1, 1, 1, 0, 10),
(2, 1, 2, 0, 10),
(3, 2, 3, 0, 8),
(4, 2, 4, 0, 8),
(5, 3, 5, 0, 5),
(6, 4, 6, 0, 6),
(7, 5, 7, 0, 3);

-- Inserting data for Lounges with unique Queues
INSERT INTO Lounge (id, parent_terminal, capacity, access_fee, queue_id)
VALUES 
(1, 1, 300, 120.0, 8),
(2, 1, 250, 100.0, 9),
(3, 2, 200, 80.0, 10),
(4, 3, 150, 150.0, 11),
(5, 4, 100, 70.0, 12),
(6, 5, 50, 90.0, 13);

-- Inserting data for Services Offered
INSERT INTO ServicesOffered (id, description)
VALUES 
(1, 'High-Speed Wi-Fi'),
(2, 'Gourmet Dining'),
(3, 'Private Showers'),
(4, 'VIP Meeting Rooms'),
(5, 'On-Demand Entertainment'),
(6, 'Personalized Assistance'),
(7, 'Luggage Storage'),
(8, 'Spa Services');

-- Associating Lounges with Services
INSERT INTO Lounge_Services (service_id, lounge_id)
VALUES 
(1, 1), (2, 1), (3, 1), (4, 1),
(5, 2), (1, 2), (6, 2),
(2, 3), (7, 3), (5, 3),
(1, 4), (6, 4), (8, 4),
(2, 5), (1, 5), (7, 5),
(3, 6), (4, 6), (5, 6);

-- Inserting a user for Airport Staff
INSERT INTO User (id, name, password, email, type, profile_id)
VALUES 
(1, 'Michael Anderson', 'michaelSecurePass123', 'm.anderson@airport.com', 'Staff', NULL);

-- Inserting a profile for the Airport Staff User
INSERT INTO AirportStaff (id, name, phone, cncic, gender, login_id)
VALUES 
(1, 'Michael Anderson', '9988776655', 'CNIC87654321', 'M', 1);

-- Updating the User table to link the profile_id
UPDATE User
SET profile_id = 1
WHERE id = 1;
SELECT * FROM Terminal