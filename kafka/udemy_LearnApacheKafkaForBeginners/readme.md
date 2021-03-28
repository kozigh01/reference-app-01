# Udemy: Apache Kafka Series - Learn Apache Kafka for Beginners v2

Course: [Udemy](https://www.udemy.com/course/apache-kafka/)

## Resources
* Java on Ubuntu: [install](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-20-04)
    * instructions recap:
    ```
    $ sudo apt update
    $ sudo apt install default-jre
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
* Run 