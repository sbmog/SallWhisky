# Sall Whisky Distillery - Production & Inventory Management

This project was developed as part of the 2nd semester of the Computer Science program. The objective was to create a specialized IT system to manage production workflows and inventory for a modern distillery.

## Project Description
The system digitalizes the entire process from receiving malt to the finished product. It ensures full traceability and provides an overview of the whisky maturation process, warehouse locations, and quality control.

### Key Features
* **Production Management:** Registration and overview of Malt Batches, Distillates, and the subsequent filling into casks.
* **Advanced Inventory Management:** Visual handling of the warehouse via a TreeView structure, covering warehouses, racks, and specific shelf positions.
* **Traceability:** Ability to trace a specific bottle or cask back through the entire production history (from cask to distillate to malt).
* **Maturation Control:** Logic for calculating the "Angel's Share" (evaporation) and handling the re-racking (transfer) between casks.
* **Bottling & Dilution:** Management of the process from matured whisky to finished bottles, including alcohol percentage calculations during dilution.

## Technical Stack
* **Language:** Java (JDK 17+)
* **GUI:** JavaFX (utilizing custom panes, TreeView, and CSS styling)
* **Testing:** JUnit for unit testing of business logic
* **Development Methodology:** Unified Process (UP)
* **Tools:** IntelliJ IDEA, Git, UML (Enterprise Architect/StarUML)

## Architecture & Methodology
The project is built using a layered architecture to ensure separation of concerns:
1. **GUI Layer:** Handles user interaction via JavaFX.
2. **Controller Layer:** Acts as a link between the UI and the domain model (Controller pattern).
3. **Model Layer:** A complex domain model reflecting the physical reality of the distillery.
4. **Storage Layer:** Centralized storage of the system state.

During development, significant emphasis was placed on **System Development (SD)**, including the creation of Use Cases, Domain Models, and Design Class Diagrams to ensure a robust software architecture.

## Authors
Karsten Kirkegaard, Rune Hyldgaard Jensen, and Sidse Borch Mogensen
