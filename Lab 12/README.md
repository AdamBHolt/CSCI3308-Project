## CSCI3308 Lab12

### Preperation
You are required to **fork** this repository to your own Github account. Then you can **clone** your forked repository to your local computer.

Go through the following links to know more about Facade pattern and Adapter pattern.  
* [Facade Pattern](http://sourcemaking.com/design_patterns/facade)  
* [Adapter Pattern](http://sourcemaking.com/design_patterns/adapter)  
* [Comparison of Facade and Adapter](http://www.netobjectives.com/blogs/adapter-and-facade)  

### Facade Pattern
Imagine that you have some parts of a computer, which is implemented as classes in the *parts.py* file. Now you want to simplify them into one single Computer class. How can you make it?

Go through the code in *parts.py* and *façade.py*. Implement the facade pattern.

### Adapter Pattern
Now you have a Computer class in *façade.py*. But the problem is that I have some running code that wants to call a class which represents a computer. The code can’t call the Computer class directly because the code wants to call some methods that are different from those of the Computer class. How can you implement an adaptor without modifying the running code I have?

Go through the code in *adapter.py*. Implement the Adapter class. Remember that **you can only use the Computer class** to implement the Adapter class.

### Submit
After you implement all the required code, **commit** your modification and **push** it to **your** Github repository. Then submit the **link** to your Github repository to Moodle.
