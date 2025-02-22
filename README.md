# TuneUp Application by JETA

## Purpose

This is a music learning app.

## Features

- **User Profiles**: Create and manage user profiles with different roles (Teacher, Student) and experience levels (Beginner, Intermediate, Advanced).
- **Song Library**: Browse, add, and remove songs from the library. View song details and open PDF files of the songs.
- **Lessons**: Teachers can create, assign, and manage lessons for students. Students can view lessons based on their experience level.
- **Play Mode**: Play notes using different instrument strategies (e.g., Piano).
- **Create Mode**: Compose and save music by adding notes to a composition.

## Getting Started

1. **Build the project using Maven**:
    ```sh
    mvn clean install
    ```

2. **Run the application**:
    ```sh
    java -jar target/tuneup-1.0-SNAPSHOT.jar
    ```

## Documentation

- Checkout the [Requirements Document](https://github.com/allanpaiz/JETA/blob/main/docs/specification_doc.pdf).
- Checkout the [UML Class Diagram](https://github.com/allanpaiz/JETA/blob/main/docs/UML_class_diagram.pdf).
- Read the [UML Sequence 1 Document](https://github.com/allanpaiz/JETA/blob/main/docs/UML_Sequence_1.pdf).
- Read the [UML Sequence 2 Document](https://github.com/allanpaiz/JETA/blob/main/docs/UML_Sequence_2.pdf).


## Dependencies
- [Gson](https://github.com/google/gson) for JSON parsing.
- [iTextPDF](https://itextpdf.com/en) for handling PDF files.

## Authors
- JETA Team

## Contact
For any inquiries or feedback, please contact us at [support@tuneup.com](mailto:support@tuneup.com).
