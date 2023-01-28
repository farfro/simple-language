//Name: Saimon Asghedom
//course: CS202
//file: in this file I'm going to create a class that will serve to hold variables
//variables that declared will have the name provided, literal string and integers will
//also have variables with the name literal
public class variable extends util {
    protected String name;
    protected int value;

    public variable(String name){       //constructor with string param
        this.name = name;
        this.value = 0;
    }
    public variable(String name, int value){    //constructor that take string and int
        this.name = name;
        this.value = value;
    }
    public variable(variable source){       //copy contructor
        this.name = source.name;
        this.value = source.value;
    }
    public void insert(variable source){    //overloaded function to add a value to a variable obj
        this.value = source.value;
    }
    public void insert(int value){          //overloaded function to add a value to a variable obj
        this.value = value;
    }
    public void display(){                  //function that will display the content of a variable obj
        if(this.name != null)
          System.out.println("Name: " + this.name);
        if(this.value != 0){
            System.out.println("Value: " + this.value);
        }
    }

    public boolean compare(variable source){    //function to compare two variable obj
        return this.name.equals(source.name);
    }
    public int add(variable source){            //function to add the values of two variables
        return this.value + source.value;
    }
    public int subtract(variable source){       //function to subtract the values of two variables
        return this.value - source.value;
    }

}
