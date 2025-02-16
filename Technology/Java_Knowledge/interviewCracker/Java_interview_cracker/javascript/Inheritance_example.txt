
Inheritance in JavaScript: Understanding the constructor Property
===================================================================
function Base2() {}
function Derived2() {}
// Set the `[[Prototype]]` of `Derived.prototype`
// to `Base.prototype`
Derived1.prototype=Object.create(Base.prototype);

we'll loose the constructor of Derived1
to fix this we also need to add

Derived1.prototype.constructor = Derived1;

function Base() {}
function Derived1() {}
// Set the `[[Prototype]]` of `Derived.prototype`
// to `Base.prototype`
Object.setPrototypeOf(Derived1.prototype, Base.prototype);


======================

The constructor in a prototype object
The constructor property in a prototype is automatically setup to reference the constructor function.

function Cat(name) {
  this.name = name;
}
Cat.prototype.getName = function() {
  return this.name;
}
Cat.prototype.clone = function() {
  return new this.constructor(this.name);
}
Cat.prototype.constructor === Cat // => true
Because properties are inherited from the prototype, the constructor is available on the instance object too.


var catInstance = new Cat('Mew');
catInstance.constructor === Cat // => true
Even if the object is created from a literal, it inherits the constructor from Object.prototype.

var simpleObject = {
  weekDay: 'Sunday'
};
simpleObject.prototype === Object // => true
2.1 Don't loose the constructor in the subclass
constructor is a regular non-enumerable property in the prototype object. It does not update automatically when a new object is created based on it. When creating a subclass, the correct constructor should be setup manually.

The following example creates a sublcass Tiger of the Cat superclass. Notice that initially Tiger.prototype still points to Cat constructor.

function Tiger(name) {
   Cat.call(this, name);
}
Tiger.prototype = Object.create(Cat.prototype);
// The prototype has the wrong constructor
Tiger.prototype.constructor === Cat   // => true
Tiger.prototype.constructor === Tiger // => false
Now if we clone a Tiger instance using clone() method defined on Cat.prototype, it will create a wrong Cat instance.


To fix this problem it's necessary to manually update the Tiger.prototype with the correct constructor function: Tiger. The clone() method will be fixed too.

//Fix the Tiger prototype constructor
Tiger.prototype.constructor = Tiger;
Tiger.prototype.constructor === Tiger // => true

var tigerInstance = new Tiger('RrrMew');
var correctTigerClone = tigerInstance.clone();

// Notice that correctTigerClone is correctly a Tiger instance
correctTigerClone instanceof Tiger  // => true
correctTigerClone instanceof Cat    // => false



