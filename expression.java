//Name: Saimon Asghedom
//course: CS202
//file: in this file I will create a a pure abstract base class that will be used to drive three classes

interface expression{   //class expression, has no data members and have one function
    int math();
}
//class plus which will be derived from class expression
class plus implements expression{
    protected variable op1;     //two variable data members
    protected variable op2;

    public plus(variable op1, variable op2){    //constructor that take two variables
        this.op1 = new variable(op1);
        this.op2 = new variable(op2);
    }

    public int math(){
        return op1.add(op2);
    }   //function that will be adding the variables and returning the result

}
//class minus will be derived from class expression
class minus implements expression{
    protected variable op1;     //two variable data members
    protected variable op2;

    public minus(variable op1, variable op2){   //constructor that take two variable obj
        this.op1 = new variable(op1);
        this.op2 = new variable(op2);
    }

    public int math(){
        return op1.subtract(op2);
    }   //function that will be subtracting the variables and return the result
}
//class equals which will be derived from class expression
class equals implements expression{
    protected variable op1; //have two variable obj as data members
    protected variable op2;

    public equals(variable op1, variable op2){  //constructor that take two variable obj
        this.op1 = new variable(op1);
        this.op2 = new variable(op2);
    }

    public int math(){      //will assign the value of the second variable to the first and return the second vari
        op1.insert(op2);
        return op2.value;
    }
}
