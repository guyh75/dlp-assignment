# DLP Assignment

### Execution instructions
This is a Spring Boot application. You need to run src\main\java\assignment\proofpoint\dlp\DlpAssignmentApplication.java main class

To call the REST service, you can use swagger ui:

open http://localhost:8080/swagger-ui/ and use the validation controller.

For testing the file controller, use an absolute path to a text file on your machine, or use the project file test/resources/content.txt

### Assignment notes

- Context keywords match is set to ignore-case. This can be replaced with a feature flag per validator.
- The file validation operator is a simple and naive solution due to time limit with submitting this project.
However, it wasn't clear by the requirements which file size should be supported.
Assuming it can be a very large file, then maybe files should be processed by reading and processing them in chunks.