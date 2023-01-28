//Name: Saimon Asghedom
//course: CS202
//In this file I'm going to create a class that will manage a table of files

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

//class red_black will manage the data structure that is going to be implemented
public class red_black extends util {
    //class node will be used to create the data structure
    class node {
        protected String name;
        protected String filename;
        protected String color;
        protected node left;
        protected node right;
        protected node parent;

        public node() { //default constructor
            name = "";
            filename = "";
            color = "";
            left = null;
            right = null;
            parent = null;
        }

       public node(String name, String file) {  //constructor with arguments that will set color to red
            this.name = name;
            this.filename = file;
            this.color = "red";
            left = null;
            right = null;
            parent = null;
        }

    }

    protected node root;    //declare node root

    public red_black() {    //default constructor of red_black
        root = null;
    }
    public void display_all()   //function that will display all
    {
        if(root == null) return;

        display_all(root);      //call to a recursive function to display all
    }

    public void display_all(node root)  //recursive function to display all
    {
        if(root == null) return;

        display_all(root.left);
        System.out.println(root.name + ":  " + root.filename);
        display_all(root.right);
    }

    public int retrieve(String name, String[] file){    //function to retrieve a file from the table
        if(root == null) return 0;

        return retrieve(root, name, file);  //call to a recursive function to retrieve a file
    }

    public int retrieve(node root, String name, String[] file){ //recursive function for retrieval

        if(root == null) return 0;

        int call = 0;

        if(name.equals(root.name)){ //if passed in name matches the name then retrieve their file
            file[0] = root.filename;
            return 1;
        }

        call = retrieve(root.left, name, file);
        call += retrieve(root.right, name, file);
        return call;
    }
    public void insert(String name, String file) {  //function to insert files into the table
        if (this.root == null) {
            root = new node(name, file);    //if root is null then add the first node and set it to black
            this.root.color = "black";
            return;
        }

        node[] obj = new node[1];       //to store the new node thats been added
        this.root = insert(root, root, obj, name, file);    //otherwise call the recursive insert function
        insert_fix(obj[0]); //fix the tree after the new node
    }

    public node insert(node root, node parent, node[] obj, String name, String file) {  //recursive insert function
        if (root == null) {
            root = new node(name, file);    //add a new node
            root.parent = parent;           //set the parent to be the previous node
            obj[0] = root;                  //set the obj array to reference the new node and return root to
            return root;                    //be attached to the tree
        }

        if (name.compareTo(root.name) < 0){
            root.left = insert(root.left, root, obj, name, file);   //go left
        }
        else {
            root.right = insert(root.right, root, obj, name, file); //go right
        }

        return root;    //return root
    }

    public void insert_fix(node root)
    {
        if(root == this.root)          //check if the node is root
            return;
        node temp;

        if(root.color.equals("red") && root.parent.color.equals("red"))      //if parent color is red then fix
        {
            node parent = root.parent;         //parent gets the nodes parent
            node grandparent = parent.parent;  //gandparent gets the nodes parents parent
            node uncle;

            if(parent == grandparent.left)        //chack if parent is to the left of grandparent
            {
                if(grandparent.right != null)             //if it is, check if grandparent has a right node
                {
                    uncle = grandparent.right;        //uncle gets grandparent's right node
                    if(uncle.color.equals("red"))           //if uncle color is red then
                    {                                       //set parent and uncle color to black
                        parent.color = "black";         //temp to grandparent
                        uncle.color = "black";
                        temp = grandparent;
                        if(grandparent != this.root)   //check if grandparent is root, change color to red
                        {                               //if not root
                            grandparent.color = "red";
                        }
                    }
                    else if(root == grandparent.left.left) //uncle's color is not red then
                    {                                                  //if root is the most left to grandp
                        grandparent.color = "red";               //set grandp colot to red and parent to
                        parent.color = "black";                    //black and right rotate
                        temp = parent;
                        right_rotate(grandparent);
                    }
                    else
                    {
                        temp = parent;                          //otherwise leftrotate if root is grandpa
                        left_rotate(parent);                     //left and right
                    }

                }
                else if(root == grandparent.left.right)  //if grandpa right is empty then
                {                                                    //if root is left and right of grandpa
                    temp = parent;                               //then left rotate
                    left_rotate(parent);
                }
                else
                {                                                    //otherwise if root is most left of grandpa
                    parent.color = "black";                      //set parent color to black and grandpa color
                    grandparent.color = "red";                 //to red and right rotate
                    temp = parent;
                    right_rotate(grandparent);
                }
            }
            else
            {                                                   //if parent is to the right of grandpa
                if(grandparent.left != null)                      //check if there is a grandpa left node
                {
                    uncle = grandparent.left;              //uncle gets grandparent's left node
                    if(uncle.color.equals("red"))                //if uncle color is red then
                    {                                            //set parent and uncle color to black
                        parent.color = "black";                 //check if grandparent is root, change color to red
                        uncle.color = "black";                  //if not root
                        temp = grandparent;
                        if(grandparent != this.root)
                        {
                            grandparent.color = "red";
                        }
                    }
                    else if(root == grandparent.right.right)  //uncle's color is not red then
                    {                                                     //if root is the most right to grandp
                        grandparent.color = "red";                    //set grandp color to red and parent to
                        parent.color = "black";                         //black and left rotate
                        temp = parent;
                        left_rotate(grandparent);
                    }
                    else
                    {
                        temp = parent;                                //otherwise right rotate if root is grandpa
                        right_rotate(parent);                          //if right and left
                    }

                }
                else if(root == grandparent.right.left)    //if grandpa right is empty then
                {                                                      //if root is right and left of grandpa
                    temp = parent;                                  //then right rotate
                    right_rotate(parent);
                }
                else
                {                                                      //otherwise if root is most right of grandpa
                    parent.color = "black";                         //set parent color to black and grandpa color
                    grandparent.color = "red";                    //to red and left rotate
                    temp = parent;
                    left_rotate(grandparent);
                }
            }
        }
        else                                                            //if parent color is not red then set
        {                                                               //temp to the root
            temp = this.root;
        }

        insert_fix(temp);                                               //recussive call
    }


    public void left_rotate(node p) {
        //System.out.println("Leftrotate");
        node hold = p.right;
        p.right = hold.left;

        if (p.right != null)        //if p's right is not null update it parent to be p
            p.right.parent = p;

        hold.parent = p.parent;

        if (p.parent == null)       //if p's parent is null then update the root to hold
            this.root = hold;
        else if (p == p.parent.left)  //if p is to the left of its parent then update the parent's left tp hold
            p.parent.left = hold;
        else
            p.parent.right = hold;  //if p is to the right of it parent then update tht parent's right to hold

        hold.left = p;
        p.parent = hold;
    }

    public void right_rotate(node p) {
        //System.out.println("Rightrotate");
        node hold = p.left;
        p.left = hold.right;

        if (p.left != null)         //if p's left is not null update it parent to be p
            p.left.parent = p;

        hold.parent = p.parent;

        if (p.parent == null)       //if p's parent is null then update the root to hold
            this.root = hold;
        else if (p == p.parent.left)    //if p is to the left of its parent then update the parent's left tp hold
            p.parent.left = hold;
        else                            //if p is to the right of it parent then update tht parent's right to hold
            p.parent.right = hold;

        hold.right = p;
        p.parent = hold;

    }

    public void check_file(String filename) {
        String tokens;
        String line;
        int count = 0;
        boolean result = true;
        tree check;
        try {
            //filename = System.getProperty("user.dir") + "/src/" + filename;
            File file = new File(filename);
            Scanner infile = new Scanner(file);

            while (infile.hasNext()) {  //count the number of lines in the file
                //++count;
                line = infile.nextLine();
                if (!line.isEmpty()) {
                    ++count;
                }
            }
            infile.close(); //close file
            infile = new Scanner(file); //open file again and read one line at a time and pass it
            check = new tree(count);      //to be parsed in token and get checked for errors
            int error = 0;
            while (infile.hasNext() && result) {
                tokens = infile.nextLine();
                if (!tokens.isEmpty()) {
                    result = check.add(tokens);
                    if (!result) { //if an error is found, display the line number and line
                        System.out.println("ERROR: There was an error in Line " + ++error + ". " + tokens);
                    }
                    ++error;
                }

            }
            if(result){
                System.out.println("There was no error with the code..");
            }
            infile.close();     //close file, print if no error are found

        } catch (FileNotFoundException e) {    //catch no file exception and print the error message
            System.out.println("Error has occurred");
            e.printStackTrace();
        }

    }

}