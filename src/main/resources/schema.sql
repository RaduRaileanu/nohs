CREATE TABLE if NOT EXISTS organisation
(
    id             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name      varchar(255),
    code     varchar (255),
    service_type            varchar (25),
    trial_requests integer, 
    created_at   timestamp,
    updated_at timestamp
);