### Private Investigator
##### Private Investigator is a command line application that reads the input text file and generates the investigation report in the output file.

#### Overview of the solution:

The main class PrivateInvestigatorApplication implements the CommandLineRunner interface and is responsible for starting the Spring application. The application reads in a list of sentences from a file specified in inputFilePath property, passes the sentences to PrivateInvestigator for processing, and writes the results to an output file specified in outputFilePath property.

`PrivateInvestigator` has several dependencies including `InvestigationResultHolder`, `Sentences`, and `UUIDGenerator`. The `initiateInvestigation` method in `PrivateInvestigator` loops through each sentence in the list and performs a validation check. If a sentence is valid, it creates a SentenceHolder object with the sentence, and passes it to the check method in InvestigationResultHolder. The check method generates a unique key based on the words in the sentence, with one word skipped, and maps the key to the sentence's ID.
It updates the `similarSentences` which is a list of `similarSentenceMapKey` and `similarSentenceMapping` which is a global Map of mapping between `similarSentenceMapKey` and Set of sentence IDs. `similarSentenceMapKey` is the concatenated value of unique Sentence combination with one word skipped and the index of the skipped word using pipe symbol `|`.


`InvestigationResultHolder` keeps track of similar sentences by mapping their keys to the sentence IDs. When all sentences have been processed, the `writeResults` method in `InvestigationResultFormatter` formats the results and writes them to an output file. The results consist of a string with expected formatted output.
Ex:

```
01-01-2012 19:45:00 Naomi is getting into the car
02-01-2012 09:13:15 George is getting into the car
03-01-2012 11:22:40 Mike is getting into the car
The changing word was: Naomi, George, Mike
```

#### How to run?

* Navigate to the project directory:
   ```cd private-investigator```

* Run the following command to build the project:
   ```./gradlew clean build```

* Once the build is successful, run the following command to start the application:
   ```./gradlew :bootRun```

---
#### 1. What can you say about the complexity of your code?
The code has a time complexity of O(n^2) as it loops through each sentence in the input and then loops through each possible combination of words in a sentence. The space complexity is O(n) as it uses a HashMap and a Set to store the mapping of sentence combinations with sentence IDs and a set of unique sentence combinations, respectively.

#### 2. How will your algorithm scale?
 
* Load Balancing: If the application is receiving a large number of requests, we can distribute the load among multiple instances of application running in different servers. This will help reduce the burden on a single server and increase the overall capacity of the application.

* Caching: Implementing caching can significantly improve the performance of the application. We can push the processed result into the cache and process those messages via a cron job which can read the messages from cache, writes to a file or database and then deletes it from the cache.

* Messaging: Rather than processing all the input at once. We can push the input data into a messaging system such as Kafka or RabbitMQ and process those messages at fixed rate.

* Parallel Processing: We can divide the workload into smaller parts and process them in parallel. This can help reduce the overall processing time and increase the speed of the application.

#### 3. If you had two weeks to do this task, what would you have done differently? What would be better?

I would integrate the system with a database and store the results in a table
Expose API to upload input file(s), initiate the investigation and write the results.

It would have been more better to integrate it with messaging system, a cron job and have a listener.
Listener function would push the input data to a topic if there will be any change in the input file and the cron job will process the input from the same topic and update the result in a database or a file.

---

#### Input
> `input.txt`is located in the `src/resource/data/` directory. The input file contains a list of sentences ordered by time, one sentence per row. Each sentence has a date-time stamp and a description of the activity being performed by the person being followed.

#### Output
> The application will run and generate the report in the `output.txt` file located in the `src/resource/data/` directory.

