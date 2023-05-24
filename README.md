# CC-WebCrawler
![build](https://github.com/Clemi2806/CC-WebCrawler/actions/workflows/maven.yml/badge.svg)

## Build, test, run

To run the project you must have a valid API-Key for the DeepL Translation API. Clone the project and create
a `crawler.properties` file in the root folder. Append the following line to the file:
<br> `apikey=<your api key>`

After that run the `mvn package` command to create a runnable jar. When the command has run the runnable is in
the `target` folder.

Please execute the runnable jar **_with_** dependencies.

To run the tests for the classes use the `mvn test` command.

If you are using IntelliJ, the app can be started by using the run icon in the `Main` file.
