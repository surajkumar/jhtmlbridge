# JHtmlBridge
Create Graphical User Interfaces in Java using HTML Java

## Usage
Using this library is straightforward:


1. Instantiate JHtmlBridge:
```java
JHtmlBridge htmlBridge = new JHtmlBridge("www", "Example", 500, 500);
```

2. Define Java bindings:

```java
public class MyButtonHandler {
    public void exampleButtonClick() {
        System.out.println("Hello, World!");
    }
}
// ...
htmlBridge.bind("example", new MyButtonHandler());
```

3. Launch the Window
```java
htmlBridge.launch();
```

In this example the html files are stored in the `www` folder. Sample index.html

```html
<!DOCTYPE html>
<html>
<body>
<h1>Hello, World!</h1><br>
<button onclick="example.exampleButtonClick()">Click Me!</button>
</body>
</html>
```

If you want to pass arguments to your methods, simply pass the value to the method e.g. 
```java
public void exampleButtonClick(String name) {
    System.out.println("Hello, " + name);
}
//...
<button onclick="example.exampleButtonClick('John Smith')">Click Me!</button>
```

To retrieve the text content of an element, you can use the `JHtmlBridgeHelper`. 
```java
JHtmlBridgeHelper helper = htmlBridge.getHelper();
String content = helper.getContentForElement("someId");
```

## Getting Started as a Developer

### Pre-Requisites
#### Hooks
**Important**: After cloning the repository, run the following command to install the necessary pre-commit hooks:
```
pre-commit install
```
If you don't have pre-commit installed, refer to the [pre-commit website](https://pre-commit.com/) for installation instructions.

#### Environment
Ensure you have the following tools installed:
* Java 21 or higher.
* Gradle 8.6 or higher, it is recommended to use the included Gradle wrapper

## How to Contribute
Contributions are welcomed, whether it's a minor bug fix or a major feature enhancement. To contribute:

1. Fork the Repository: Click "Fork" to create a copy in your GitHub account.
2. Create a Dedicated Branch: Make changes in a dedicated branch reflecting the nature of your contribution.
3. Implement Changes: Ensure adherence to coding guidelines and include tests if applicable.
4. Submit a Pull Request: Provide a clear summary of changes and relevant details in the PR description.
5. Collaborate and Iterate: Be open to feedback to refine your contribution and improve the framework.
