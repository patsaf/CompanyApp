# CompanyApp

Android application enabling users to setup a company, hire/fire employees, create teams, assign tasks and gather work reports.

### Overview

The company has a tree-based structure. The root is the CEO - a special type of manager. Their immediate subordinates are managers - each of whom can form their own team consisting of developers, testers, contributors, etc. 
The managers can later fire and hire their subordinates as they please, as well as they may assign various tasks. Each manager has, however, a maximum team capacity. Optionally, they may also specify their own requirements and conditions in the hiring process.
All the tasks assigned by the managers to every employee in the company may be supervised by the CEO (who may also assign tasks directly to managers) - the company's whole workload may be accessed in the **report** section.

In the process of implementation the following design patterns were used:
* **Builder Pattern** - to simplify the creation of employee instances, each of whom has multiple fields
* **Factory Pattern** - to create employees of a desired type (Managers, Developers, etc)
* **Static Factory Methods** - to determine the criteria an employee must meet to be employed by a certain manager

A tool for filling employee forms with random data is enabled as well - though the default gender is female. Therefore, to get a male employee the field "MALE" must first be selected by the gender spinner and then a male employee will be generated on "GENERATE RANDOM DETAILS" button click. Also, randomly made managers don't specify hiring conditions - those need to be filled in manually.

The application was created, designed and tested using the tools of:
* **Android Studio 3.0**
* **Android 7.0 API 24**
* **Google libraries**:
  * **Guava** - the **ComparisonChain** tool to easily gather reports from single employees in a specified order
  * **Gson** - to parse objects of custom types (such as TeamManager) to the format of JSON, which can be easily passed between activities as an intent argument
  
The layouts were specified via XML files and are adapted both for portrait and landscape mode.

### Samples

* Company setup form:

![setup](https://user-images.githubusercontent.com/18569675/36940070-53ea3b32-1f3c-11e8-9ee9-988d58111352.png)

* Pop-up requesting confirmation before firing a certain employee:

![popup](https://user-images.githubusercontent.com/18569675/36940071-551f49ca-1f3c-11e8-8b7f-c91ab343c289.png)

* Task assignment form:

![task](https://user-images.githubusercontent.com/18569675/36940072-564ffdda-1f3c-11e8-93d1-12b19eaa7189.png)

