-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS CharikaLeaners;

-- Create a new user and grant privileges
CREATE USER 'user1'@'%' IDENTIFIED BY 'ABCD789k!@';
GRANT ALL PRIVILEGES ON CharikaLeaners.* TO 'user1'@'%';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;
