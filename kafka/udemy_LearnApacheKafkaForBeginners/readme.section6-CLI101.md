# Udemy: Apache Kafka Series - Learn Apache Kafka for Beginners v2

Course: [Udemy](https://www.udemy.com/course/apache-kafka/)

## Section 6 CLI 101
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
  * Producer with key:
    * `kafka-console-producer.sh --bootstrap-server broker:29092 --topic new_key_topic --property parse.key=true --property key.separator=,`
      ```
      > key1,value1
      > key2,value2
      ```
* Consumers:
  * Show command options: `$ kafka-console-consumer.sh`
  * Consume topic: `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic`
    * must produce to topic to see anything, so use this (interactively): `kafka-console-producer.sh --bootstrap-server broker:29092 --topic first_topic`
  * Consume topic from the beginning: `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic --from-beginning`
  * Consumer groups:
    * start 2 (or more) terminal instances with same group: `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic --from-beginning --property print.timestamp=true --group my-first-app-group`
      - will only see message only show up in one terminal -- only goes to one member of a group
    * starting / stopping consumer with same group id:
      * start consumer with new group id (from beginning): `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic --from-beginning --group second-app`
        - should see all messages
      * kill that consumer
      * start consumer with the same group id (from beginning): `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic --from-beginning --group second-app`
        - should not see any old messages, only new message produced after consumer started up -- the group lives on even if there are no currencly active members
        - produce a new message: should see that one being consumed
      * kill the consumer, produce new messages and start the consumer with same group id:  `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic first_topic --from-beginning --group second-app`
        - should see only the newly produced messages get consumed
  * Consumer with keys:
    * `kafka-console-consumer.sh --bootstrap-server broker:29092 --topic new_key_topic --from-beginning --property print.key=true --property key.separator=,`
* Consumer Groups:
  * show command options: `kafka-consumer-groups.sh`
  * list: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --list`
  * describe:
    * basic: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --describe --group second-app-group`
    * members: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --describe --group second-app-group --members`
    * offsets: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --describe --group second-app-group --offsets`
    * state: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --describe --group second-app-group --state`
  * reset offsets ():
    * to-earliest: 
      * dry-run: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --group second-app --topic first_topic  --reset-offsets --to-earliest --dry-run`
      * execute: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --group second-app --topic first_topic  --reset-offsets --to-earliest --dry-run`
        * now take a look: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --describe --group second-app`
    * to-offset:
      * execute: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --reset-offsets --group second-app --topic first_topic --execute --to-offset 5`
    * shift-by: 
      * execute - forward: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --reset-offsets --group second-app --topic first_topic --execute --shift-by 1`
      * execute - backward: `kafka-consumer-groups.sh --bootstrap-server broker:29092 --reset-offsets --group second-app --topic first_topic --execute --shift-by -2`
    * others: --to-offset, --to-latest, --to-datetime, --to-current 
