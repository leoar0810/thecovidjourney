# TheCovidJourney
Interactive simulator of Covid-19 infections using graph theory and java threads. Propagation speed is determined by the use of mask and the distance between nodes.

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

<img src="https://user-images.githubusercontent.com/73978713/174461035-ddc4f32d-b51b-490a-9863-65b076ad7ba4.png" height="500">

### Built With

 - [Java Development Kit](https://www.oracle.com/java/technologies/downloads/).
 - NetBeans IDE 8.2.

## Getting Started
### Prerequisites

 - The Java Development Kit. You can find it on [Oracle](https://www.oracle.com/java/technologies/downloads/).
 - [Apache NetBeans](https://netbeans.apache.org) (preferrable over NetBeans IDE 8.2).

You can clone this repsitory to get the source code

    git clone https://github.com/vergaraldvm/TheCovidJourney.git

## Usage

 1. Open the project with your IDE.
 2. Run the class `main.TheCovidJourney.java`.
 
 <img src="https://user-images.githubusercontent.com/73978713/174461034-9a2498ab-2c4a-468a-b840-0b6e65b16bad.png" height="400">
 <img src="https://user-images.githubusercontent.com/73978713/174461036-50001d58-d061-4239-9e37-1326789fd2e0.png" height="400">
 
## Build

The process of generating a .jar executable is guided by your IDE. If using NetBeans, please copy the `src/res/` path into the generated `dist/` folder.

## Authors

 - Leonardo Lizcano - [LeoLizc](https://github.com/LeoLizc)
 - Leonardo Vergara - [LeonardoVergara](https://github.com/LeonardoVergara)
 - Leonardo Aguilera - [leoar0810](https://github.com/leoar0810)
