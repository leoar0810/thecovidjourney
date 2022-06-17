# TheCovidJourney
Interactive simulation of Covid-19 infections using graph theory. Propagation speed is determined by the use of mask and the distance between nodes.

|Infected person|Uninfected person|Distance (m)|Probability (%)|
|--|--|--|--|
|No mask|No mask|>2|80|
|No mask|No mask|<=2|90|
|No mask|Mask|>2|40|
|No mask|Mask|<=2|60|
|Mask|No mask|>2|30|
|Mask|No mask|<=2|40|
|Mask|Mask|>2|20|
|Mask|Mask|<=2|30|

<img src="https://raw.githubusercontent.com/LeonardoVergara/TheCovidJourney/main/.github/images/simul.png" height="500">

### Built With

 - [Java Development Kit](https://www.oracle.com/java/technologies/downloads/).
 - NetBeans IDE 8.2.

## Getting Started
### Prerequisites

 - The Java Development Kit. You can find it on [Oracle](https://www.oracle.com/java/technologies/downloads/).
 - [Apache NetBeans](https://netbeans.apache.org) (preferrable over NetBeans IDE 8.2).

You can clone this repsitory to get the source code

    git clone https://github.com/LeonardoVergara/TheCovidJourney.git

## Usage

 1. Open the project with your IDE.
 2. Run the class `main.TheCovidJourney.java`.
 
 <img src="https://raw.githubusercontent.com/LeonardoVergara/TheCovidJourney/main/.github/images/params.png" height="400">
 
## Build

The process of generating a .jar executable is guided by your IDE.

## Authors

 - Leonardo Lizcano - [LeoLizc](https://github.com/LeoLizc)
 - Leonardo Vergara - [LeonardoVergara](https://github.com/LeonardoVergara)
 - Leonardo Aguilera - [leoar0810](https://github.com/leoar0810)
