# Udemy: Apache Kafka Series - Learn Apache Kafka for Beginners v2

Course: [Udemy](https://www.udemy.com/course/apache-kafka/)

## Resources
* Java on Ubuntu: [install](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-20-04)
    * instructions recap:
    ```
    $ sudo apt --yes update
    $ sudo apt --yes upgrade
    $ sudo apt --yes install default-jre
    $ java -version
    ```

    * test with: `java -version`
* Insall Kafka: [install](https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-20-04)
  * instructions recap:
    ```
    $ sudo adduser kafka
    $ sudo adduser kafka sudo
    $ su -l kafka
    $ mkdir ~/Downloads
    $ curl "https://downloads.apache.org/kafka/2.6.1/kafka_2.13-2.6.1.tgz" -o ~/Downloads/kafka.tgz
    $ mkdir ~/kafka && cd ~/kafka
    $ tar -xvzf ~/Downloads/kafka.tgz --strip 1
    $ export PATH="/home/kafka/kafka/bin:$PATH"
    ```
  * kafka pw: kafka

## Course Sections

### Section 6 CLI 101
* Topics:
  * Show command options: `$ kafka-topics.sh`
  * List topics: `$ kafka-topics.sh --bootstrap-server broker:29092 --list`
  * Create topic: `$ kafka-topics.sh --bootstrap-server broker:29092 --topic first_topic --create --partitions 3 --replication-factor 1`
  * Get info on topic: `$ kafka-topics.sh --bootstrap-server broker:29092 --topic first_topic --describe`
  * Delete topic: `$ kafka-topics.sh --bootstrap-server broker:29092 --topic first_topic --delete`
* Producers:
  * Show command options: `$ kafka-console-producer.sh`
  * Produce interactively: `$ kafka-console-producer.sh --bootstrap-server broker:29092 --topic first_topic`
    * cntl c: to stop producing
  * Produce with producer property: `$ kafka-console-producer.sh --bootstrap-server broker:29092 --topic first_topic --producer-property acks=all`
  * Produce to new topic will create topic: `$ kafka-console-producer.sh --bootstrap-server broker:29092 --topic new_topic`
    * defaults newly created topic to 1 partision and 1 replica.  Can change this by editing config/server.properties
* Consumers:
  * Show command options: `$ kafka-console-consumer.sh`