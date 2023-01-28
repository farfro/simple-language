//Name: Saimon Agshedom
//course: CS202
//file: in this file I'm going to create a data structure that will be used to parse the code
//entered
import java.util.ArrayList;
//class tree will manage the data structure
public class tree {
    class node{ //class node will be used to create the data structure
        String type;    //to hold the parsed type, variable, operator or command
        protected variable value; //to hold the variable with its value
        protected expression pun; //to hold the operator type
        protected node left;
        protected node right;
        public node(){          //default constructor
            this.type = null;
            this.value = null;
            this.pun = null;
            this.left = null;
            this.right = null;
        }
        //constructor that will take a string value
        public node(String value){//default constructor
            this.type = value;
            this.value = null;
            this.pun = null;
            this.left = null; //do i need new redundant
            this.right = null;
        }
        //constructor that will take string and variable type references
        public node(String value, variable token){
            this.type = value;
            this.value = new variable(token);
            this.pun = null;
            this.left = null;
            this.right = null;
        }
        //constructor that will take a variable type references
        public node(variable token) {
            this.type = "variable";
            this.value = new variable(token);
            this.pun = null;
            this.left = null;
            this.right = null;
        }
        //constructor that will take string, expression and variable type references
        public node(String val, expression operator, variable token) {
            this.type = val;
            this.value = new variable(token);
            this.pun = operator;
            this.left = null;
            this.right = null;
        }
        //function to display the information contained within each node
        public void display(){
            System.out.println("Type: " + this.type);
            /*if(this.pun != null){
                System.out.println("After doing operations: " + this.pun.math());
                System.out.println();
            }*/
            if (this.value != null){
                this.value.display();
                System.out.println();
            }
        }

        //function to set the left
        public void set_left(node left) {
            this.left = left;
        }
        //function to set the right
        public void set_right(node right){
            this.right = right;
        }
        //function to get the left
        public node get_left() {//generic type?
            return this.left;
        }
        //function to get the right
        public node get_right(){
            return this.right;
        }
    }

    protected node[] root;      //declare an array of root references
    protected ArrayList<variable> declared;     //to hold an array of declared variables for later use
    protected int size;         //to hold the size of the array of trees

    public tree(int size){      //constructor that will set the size of the array,
        root = new node[size];
        declared = new ArrayList<>(); //create an array to hold variables
        this.size = size;
    }
    //function that will display the info on the array of trees
    public void display(){
        if(root == null) return;    //return if empty
        int index = 0;
        display(root, index);       //call recursive function to output each tree in the array
    }
    //recursive function to output each tree
    public void display(node[] root, int index){
        if(index == size){
            return;
        }
        display(root[index]);       //calls recursive function to output individual trees
        display(root, ++index);     //iterate through the array
    }
    //recursive function to output individual trees
    public void display(node root){
        if(root == null) return;

        display(root.get_left());
        root.display();
        display(root.get_right());
    }
    //function that will be used to parse the code into tokens and arrange them
    //in individual trees and save them to the array of trees when there is a plus
    //operator involved
    public void thePlus(variable op1, variable op2, variable op3, int index){
        expression operator1 = new plus(op2, op3);      //upcasting to refer to a plus obj that take two variable type
        int value = operator1.math();                   //perform the operation on the two variables
        variable answer1 = new variable("answer", value);   //save their answer
        op1.insert(value);  //add the new value to variable behind the = sign
        declared.add(op1);  //and save it to the array of declared variables
        expression operator2 = new equals(op1, answer1);  //upcasting to refer to a equal obj that also takes two variables
        variable answer2 = new variable("answer", operator2.math());    //perform operation on the variables and save them to new var
        root[index] = new node("=", operator2, answer2);    //set root node to hold the = sign and the expression obj, and answer var
        root[index].set_left(new node(op1)); //set root's left to hold the variable on the left side of the = sign
        root[index].set_right(new node("+", operator1, answer1));   //set root right to hold the + , expression obj and answer var
        node hold = root[index].get_right();    //traverse to root's right node
        hold.set_left(new node(op2));           //add the two variable to the left and right of hold
        hold.set_right(new node(op3));          //these are the variables that are being added
    }
    //function that will be used to parse the code into tokens and arrange them
    //in individual trees and save them to the array of trees when there is a minus
    //operator involved
    public void theMinus(variable op1, variable op2, variable op3, int index){
        expression operator1 = new minus(op2, op3);     //upcasting to refer to a minus obj that take two variable type
        int value = operator1.math();       //perform the operation on the two variables
        variable answer1 = new variable("answer", value);   //save their answer to a variable
        op1.insert(value);      //add the new value to variable behind the = sign
        declared.add(op1);      //and save it to the array of declared variables
        expression operator2 = new equals(op1, answer1);    //upcasting to refer to a equal obj that also takes two variables
        variable answer2 = new variable("answer", operator2.math());    //perform operation on the variables and save them to new var
        root[index] = new node("=", operator2, answer2);     //set root node to hold the = sign and the expression obj, and answer var
        root[index].set_left(new node(op1));    //set root's left to hold the variable on the left side of the = sign
        root[index].set_right(new node("-", operator1, answer1));   //set root right to hold the - , expression obj and answer var
        node hold = root[index].get_right();    //traverse to root's right node
        hold.set_left(new node(op2));           //add the two variable to the left and right of hold
        hold.set_right(new node(op3));          //these are the variables that are being subtracted
    }
    //function that will be used to parse the code into tokens and arrange them
    //in individual trees and save them to the array of trees when there is only the =
    //sign involved
    public void theEqual(variable op1, variable op2, int index){
        expression operator = new equals(op1, op2); //upcasting to refer to a equal obj that also takes two variables
        int value = operator.math();                 //perform the operation on the two variables
        variable answer = new variable("initialization", value);     //save their answer to a variable
        op1.insert(value);   //add the new value to variable behind the = sign
        declared.add(op1);   //and save it to the array of declared variables
        root[index] = new node("=", operator, answer);  //set root node to hold the = sign and the expression obj, and answer var
        root[index].set_left(new node(op1));         //add the two variable to the left and right of hold
        root[index].set_right(new node(op2));       //these are the operands of the = token
    }
    //function that will be used to perform operation if the first first operand
    //of the operation is a literal integer
    public boolean IfNum(String num, String[] token, int i, variable op1, int index){
        int number = Integer.parseInt(num);     //change the token to be a real number
        variable op2 = new variable("literal", number);     //save that number to a variable obj with literal as the name
        if (++i < token.length){    //if there is a next token perform further operation else assign the literal to the variable behind =
            String type = token[i];
            if (type.equals("+")){      //check if it is the + token continue, and check if there is a next token, if not return false
                if (++i < token.length){
                    String num2 = token[i];
                    if (Character.isDigit(num2.charAt(0))){     //check if the the first character of the token is a digit, if so
                        for (char c : num2.toCharArray()) {     //check the rest of the characters and return false if there are non digits
                            if (!Character.isDigit(c)) {
                                return false;
                            }
                        }
                        int number2 = Integer.parseInt(num2);      //after checking convert it to real number
                        variable op3 = new variable("literal", number2);    //assign to a variable obj with literal name
                        if (++i < token.length) {       //there is only support for two number operation only so exit if there is more
                            return false;
                        } else {
                            thePlus(op1, op2, op3, index);      //call the plus function to save them to the array of trees and declare them
                        }
                    }
                    else{
                        for (char c : num2.toCharArray()) {     //if the second operand it char character, then check the rest
                            if (!Character.isLetter(c)) {       //if there are non letters, return false
                                return false;
                            }
                        }
                        int count = 0;
                        boolean found = false;
                        variable op3 = new variable(num2);      //save the word or letter to variable and search the array of declared
                        while(count < declared.size() && !found){   //to find if it is declared
                            if(op3.compare(declared.get(count))){
                                op3 = declared.get(count);      //if so assign it the values
                                found = true;
                            }
                            ++count;
                        }
                        if(!found){     //if not declared exit
                            return false;
                        }
                        else{
                            if (++i < token.length){    //if there are more than two operand in the operation, exit
                                return false;
                            }
                            else{           //otherwise call the plus function to save them to array of trees and declared
                                thePlus(op1, op2, op3, index);
                            }
                        }
                    }
                }
                else{   //if no operand after the operator + return false
                    return false;
                }
            }
            else if(type.equals("-")){          //if the token is - instead,
                if (++i < token.length){        //check if there is a next token, if not return false
                    String num2 = token[i];
                    if (Character.isDigit(num2.charAt(0))){      //check if the the first character of the token is a digit, if so
                        for (char c : num2.toCharArray()) {      //check the rest of the characters and return false if there are non digits
                            if (!Character.isDigit(c)) {
                                return false;
                            }
                        }
                        int number2 = Integer.parseInt(num2);   //after checking convert it to real number
                        variable op3 = new variable("literal", number2);     //assign to a variable obj with literal name
                        if (++i < token.length) {       //if there are more than two operand in the operation, exit
                            return false;
                        } else {
                            theMinus(op1, op2, op3, index);     //call the minus function to save them to the array of trees and declare them
                        }
                    }
                    else{
                        for (char c : num2.toCharArray()) { //if the second operand it char character, then check the rest
                            if (!Character.isLetter(c)) {   //if there are non letters, return false
                                return false;
                            }
                        }
                        int count = 0;
                        boolean found = false;
                        variable op3 = new variable(num2);  //save the word or letter to variable and search the array of declared to find
                        while(count < declared.size() && !found){   //if it is declared
                            if(op3.compare(declared.get(count))){
                                op3 = declared.get(count);
                                found = true;
                            }
                            ++count;
                        }
                        if(!found){     //if not declared exit
                            return false;
                        }
                        else{
                           theMinus(op1, op2, op3, index);  //otherwise call the minus function to save them to array of trees and declared
                        }
                    }
                }
                else{   //if no operand after the operator - return false
                    return false;
                }
            }
            else{   //if operator other than + = - return false
                return false;
            }
        }
        else{
            theEqual(op1, op2, index);  //call to the equal function if there is only one operand after =
        }
        return true;
    }
    //function that will be used to perform operation if the first first operand
    //of the operation is a literal character
    public boolean IfChar(String num, String[] token, int i, variable op1, int index){
        int count = 0;
        boolean found = false;
        variable op2 = new variable(num);   //save the word or letter to variable and search the array of declared to find
                                            //if it is declared
        while(count < declared.size() && !found){
            if(op2.compare(declared.get(count))){
                op2 = declared.get(count);
                found = true;
            }
            ++count;
        }
        if(!found){     //if not declared return false and exit
            return false;
        }
        else{
            if(++i < token.length){     //if there is a next token perform further operation else assign the variable data to the variable behind =
                String type = token[i];
                if (type.equals("+")){      //check if it is the + token continue, and check if there is a next token, if not return false
                    if (++i < token.length){
                        String num2 = token[i];
                        if (Character.isDigit(num2.charAt(0))){ //check if the the first character of the token is a digit, if so
                            for (char c : num2.toCharArray()) { //check the rest of the characters and return false if there are non digits
                                if (!Character.isDigit(c)) {
                                    return false;
                                }
                            }
                            int number2 = Integer.parseInt(num2);   //after checking convert it to real number
                            variable op3 = new variable("literal", number2);
                            if (++i < token.length){        //checks if there are more than two operand, if so it returns false
                                return false;
                            }
                            else{                            //otherwise call the plus function to save them to the array of trees and declare them
                                thePlus(op1, op2, op3, index);
                            }
                        }
                        else{
                            for (char c : num2.toCharArray()) { //if the second operand it char character, then check the rest
                                if (!Character.isLetter(c)) {    //if there are non letters, return false
                                    return false;
                                }
                            }
                            int count2 = 0;
                            boolean found2 = false;
                            variable op3 = new variable(num2);  //save the word or letter to variable and search the array of declared
                            while(count2 < declared.size() && !found2){     //to find if it is declared
                                if(op3.compare(declared.get(count2))){
                                    op3 = declared.get(count2);     //if so assign it the values
                                    found2 = true;
                                }
                                ++count2;
                            }
                            if(!found2){    //if ot declared return false
                                return false;
                            }
                            else{
                                if (++i < token.length){    //if more than two operand return false
                                    return false;
                                }
                                else{
                                    thePlus(op1, op2, op3, index);  //call the plus function to save them to the array of trees and declare them
                                }
                            }
                        }
                    }
                    else{   //if there is no operand after the operator return false
                        return false;
                    }
                }
                else if(type.equals("-")){      //if the token is - instead,
                    if (++i < token.length){     //check if there is a next token, if not return false
                        String num2 = token[i];
                        if (Character.isDigit(num2.charAt(0))){     //check if the the first character of the token is a digit, if so
                            for (char c : num2.toCharArray()) {     //check the rest of the characters and return false if there are non digits
                                if (!Character.isDigit(c)) {
                                    return false;
                                }
                            }
                            int number2 = Integer.parseInt(num2);   //after checking convert it to real number
                            variable op3 = new variable("literal", number2);
                            if (++i < token.length) {   //if more than two operand return false
                                return false;
                            } else {
                                theMinus(op1, op2, op3, index); //call the minus function to save them to the array of trees and declare them
                            }
                        }
                        else{
                            for (char c : num2.toCharArray()) { //if the second operand it char character, then check the rest
                                if (!Character.isLetter(c)) {   //if there are non letters return false
                                    return false;
                                }
                            }
                            int count2 = 0;
                            boolean found2 = false;
                            variable op3 = new variable(num2);  //save the word or letter to variable and search the array of declared to find
                            while(count2 < declared.size() && !found2){ //if it is declared
                                if(op3.compare(declared.get(count2))){
                                    op3 = declared.get(count2);
                                    found2 = true;
                                }
                                ++count2;
                            }
                            if(!found2){    //if not declared return false
                                return false;
                            }
                            else{
                                theMinus(op1, op2, op3, index); //call the minus function to save them to the array of trees and declare them
                            }
                        }
                    }
                    else{   //if no operand after the operator return false
                        return false;
                    }
                }
                else{   //if there are operator other than + - = return false
                    return false;
                }
            }
            else{       //call the equal function if there is only one operand after the = token
                theEqual(op1, op2, index);
            }
        }
        return true;
    }

    public boolean add(String tokens){
        String[] token = tokens.split(" "); //break the statement into tokens
        int index = 0;
        int i = 0;

        while(root[index] != null){ //iterate until it find empty tree
            ++index;
        }
        if(token[i].equals("start")){       //if the token is start, store in tree available
            root[index] = new node(token[i]);
            return true;
        }
        else if(token[i].equals("end")){    //if the token is end, store in tree available
            root[index] = new node(token[i]);
            return true;
        }
        else if(token[i].equals("var")){    //if the token is var, then
            if(++i < token.length) {    //check to see if there is a next token, if not return false
                String test = token[i];
                for (char c : test.toCharArray()) {     //check to see if the next token is a letter, if not return false
                    if (!Character.isLetter(c)) {
                        return false;
                    }
                }
                variable op1 = new variable(test); //give that token to a variable obj

                if(++i < token.length){     //check for a next token, if no token then add that variable to declared and add it to
                    test = token[i];        //available tree
                    if (test.equals("=")) { //otherwise there is a token = continue, if not return false

                            if (++i < token.length) {   //check for next token, if no token return false
                                String num = token[i];
                                if (Character.isDigit(num.charAt(0))) { //otherwise check if the first character of the token is a digit
                                    for (char c : num.toCharArray()) {  //if so check the rest of them and return false if non digit
                                        if (!Character.isDigit(c)) {
                                            return false;
                                        }
                                    }
                                    return IfNum(num, token, i, op1, index);  //call the function that handle when the first operand are digits
                                }
                                else {
                                    for (char c : num.toCharArray()) {  //if the token is a character then
                                        if (!Character.isLetter(c)) {
                                            return false;
                                        }
                                    }
                                    return IfChar(num, token, i, op1, index);   //call the function that handle when the first operand are character
                                }
                            }
                            else{   //if no operand after = return false
                                return false;
                            }
                    }
                    else{   //if the token is other than the = return false
                        return false;
                    }

                }
                else{   //if it is only a declaration
                    declared.add(op1);
                    root[index] = new node(op1);
                    return true;
                }
            }
            else{   //if var but give it no name return false
                return false;
            }

        }
        else if(token[i].equals("print")){  //otherwise if the first token is print, then
                if(++i < token.length){
                    String stat = token[i];     //check if there is a next token, if not return false
                    if(stat.charAt(0) == '"'){  //if the first character of the next token is a double quote
                        StringBuilder result = new StringBuilder(); //then combine the rest of the tokens in the string array
                        for(int j = i; j < token.length; ++j){
                            result.append(token[j]);
                            result.append(" ");
                        }
                        String print = result.toString();   //convert to string
                        print = print.replaceAll("\"", ""); //get rid of the double quotes
                        variable todo = new variable(print);    //save the info in a temp variable and display
                        //todo.display();
                        root[index] = new node("print", todo);  //save that to an available tree
                    }
                    else if(Character.isLetter(stat.charAt(0))){    //if the first charater of the next token is instead a letter
                        for (char c : stat.toCharArray()) {         //then check the rest of the word, return false if non letter
                            if (!Character.isLetter(c)) {
                                return false;
                            }
                        }
                        int count = 0;
                        boolean found = false;
                        variable op = new variable(stat);       //save the word to a variable and search the declared for it
                        while(count < declared.size() && !found){
                            if(op.compare(declared.get(count))){
                                op = declared.get(count);   //if found assign it the values
                                found = true;
                            }
                            ++count;
                        }
                        if(!found){     //return false if not declared
                            return false;
                        }
                        else{   //otherwise display the info ad save to available tree
                            //op.display();
                            root[index]= new node("print", op);
                        }
                    }
                }
                else{   //if nothing after print return false
                    return false;
                }

        }
       else if(!token[i].isEmpty() && Character.isLetter(token[i].charAt(0))){ //if the first token is a character
                String letter = token[i];
                for (char c : letter.toCharArray()) {   //check the rest of the characters are letters, if not return false
                    if (!Character.isLetter(c)) {
                        return false;
                    }
                }
                int count = 0;
                boolean found = false;
                variable op = new variable(letter);     //assign the word to a variable and search the declared
                while (count < declared.size() && !found) {
                    if (op.compare(declared.get(count))) {
                        op = declared.get(count);       //if found the assign it the value
                        found = true;
                    }
                    ++count;
                }
                if (!found) {   //if not found,not declared return false
                    return false;
                } else {
                    if (++i < token.length) {   //check if there is a next token, if not return false
                        String test = token[i];
                        if (test.equals("=")) { //otherwise if that token is = continue, if not return false

                            if (++i < token.length) {   //check if there is a next token, if not return false
                                String num = token[i];
                                if (Character.isDigit(num.charAt(0))) { //otherwise if the next token is a digit,
                                    for (char c : num.toCharArray()) {
                                        if (!Character.isDigit(c)) {
                                            return false;
                                        }
                                    }
                                    return IfNum(num, token, i, op, index); //call the function that handle digit as a first operand
                                } else {
                                    for (char c : num.toCharArray()) {  //if the token is a letter
                                        if (!Character.isLetter(c)) {
                                            return false;
                                        }
                                    }
                                    return IfChar(num, token, i, op, index);    //call the function that handle character as a first operand
                                }
                            } else {    //if no operand after = return false
                                return false;
                            }
                        } else {    //if other than = when the first token is a word/letter
                            return false;
                        }

                    } else {    //if it is just a letter/word in a statement return false
                        return false;
                    }
                }

        }
        return true;
    }

}
