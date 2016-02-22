package example.com.mycontacts;

/**
 * Created by ullasjain on 2/15/16.
 */
public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    String _email;


    // Empty constructor
    public Contact() {

    }

    // constructor
    public Contact(int id, String name, String _phone_number,String _email) {
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email=_email;
    }

    // constructor
    public Contact(String name, String _phone_number,String _email) {
        this._name = name;
        this._phone_number = _phone_number;
        this._email=_email;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }


    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }
}
