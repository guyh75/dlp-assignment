# DLP Assignment

### Execution instructions
This is a Spring Boot application. You need to run src\main\java\assignment\proofpoint\dlp\DlpAssignmentApplication.java main class

To call the REST service, you can use swagger ui:

open http://localhost:8080/swagger-ui/ and use the validation controller.

For testing the file controller, use an absolute path to a text file on your machine, or use the project file test/resources/content.txt

### Code Logic
The validation service contains all validator components that implements the DlpValidator interface using spring's dependency injection.
It iterates them and collect all DLP vulnerabilities found by the validators.
Each Validator can have his own sensitive data scanner, which may execute the desired checksum on the tokens found.

A context keywords search is done using a package that implements the Aho-Corasick algorithm for keyword pattern search.

### Assumptions
Context keywords match is set to ignore-case. This can be replaced with a feature flag per validator.

### Notes
In assignment step 2(handling files), I've implemented a simple and naive file handling.
I assume there will be big files to handle, and that it may require to split the file to chunks, process each chunk
and combine the results(e.g, if a keyword context appears in the beginning of the file, and the DLP content appears at the end)

