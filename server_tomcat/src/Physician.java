public class Physician extends User{
    public String email;
    public String password;
    static String temp_role = "physician";

    public Physician(String email, String password, String name, Integer age, String gender) {
        super(name, age, gender, temp_role);
        this.email = email;
        this.password = password;
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        if (passwordLength>6 && passwordLength<9){
            return "200__Ok";
        }
        else{
            return "400__password must be between 4 and 6 characters";
        }
    }

    @Override
    public String login(){
        return "";
    }
    
}
