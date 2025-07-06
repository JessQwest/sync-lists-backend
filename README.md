# Sync Lists

This application allows you to store multiple lists on your self hosted server, with syncronisation between devices and compatability between mobile and desktop without dependance on third parties to store your information

## List types

There are 2 list types available within this application:

1. To-do lists allow you to cross items off as you complete them
2. Stock lists allow you to keep track of what items you have, with a flag button for tracking other statistics (such as needing to restock an item soon)

## Setup for Dockerised solutions
(for non dockerised solutions you'll still need to change the API as mentioned in step 2)

This is a 3 part solution, requiring the frontend application, this backend counterpart and a mysql database to store the list items. Learn more about the frontend counterpart here: https://github.com/JessQwest/sync-lists-frontend

It's recommended that you have this backend set up first.

1. Either:
   - Download the prebuilt JAR
   - Clone the repository and `gradle build` inside the directory
2. Build the container and run. I built within linux using the attached ./build.sh script

### Example docker compose 

This setup has frontend on :2080, backend on :2081 and mysql on :3307. I've exposed both frontend and backend through an authenticated cloudflare tunnel to allow secure and private access anywhere
```yaml
synclists_db:
    container_name: synclists_db
    image: mysql:8.0
    restart: always
    environment:
      MYSQL_DATABASE: 'synclists'
      MYSQL_USER: 'mainuser'
      MYSQL_PASSWORD: 'mainuserpassword' <--- CHANGE
      MYSQL_ROOT_PASSWORD: 'rootpassword' <--- CHANGE
    healthcheck:
      test: mysqladmin ping -h 127.0.0.1 -u $$MYSQL_USER --password=$$MYSQL_PASSWORD
      start_period: 5s
      interval: 5s
      timeout: 5s
      retries: 10
    ports:
      - '3307:3306'
    expose:
      - '3307'
    volumes:
      - synclists-db:/var/lib/mysql
    stdin_open: true
    tty: true
    networks:
      - synclists_network 
      
  sync-lists-backend:
    container_name: sync-lists-backend
    image: sync-lists-backend
    ports:
      - '2081:8080'
    restart: unless-stopped
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://synclists_db:3306/synclists
      - SPRING_DATASOURCE_USERNAME=mainuser
      - SPRING_DATASOURCE_PASSWORD=mainuserpassword <-- CHANGE
      - SERVER_PORT=8080
    networks:
      - synclists_network
      
  sync-lists-frontend:
    container_name: sync-lists-frontend
    image: sync-lists-frontend
    ports:
      - '2080:80'
    restart: unless-stopped
    networks:
      - synclists_network
 
volumes:
  synclists-db:

networks:
  synclists_network:
    driver: bridge
```
