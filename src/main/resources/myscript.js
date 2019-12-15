// JavaScript code inside java
var a = 10;
var b = 20;

function myJsFunction() {
    print("JS: callback function")
}

print("JS: before Async");
myAsync.doAfterDelay(2000, function () {
    print("JS: anonymous function")
});
print("JS: after Async");

// Result of the last line will be return value of the script
print("JS: " + myJavaVariable + ": " + (a + b));

var MyAnotherClassType = Java.type("MyAnotherClass");

var another = new MyAnotherClassType();
print("JS: "+ another.sum(1, 2));

"JS: return value";
