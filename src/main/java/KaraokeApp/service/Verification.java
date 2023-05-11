/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KaraokeApp.service;

import KaraokeApp.model.User;
import KaraokeApp.repository.AcountRes;

/**
 *
 * @author 84374
 */
public class Verification {

    public Verification() {
    }    
    
    public Integer verAction(String name, String pass ){
        AcountRes acc = new AcountRes();
        acc.addTolist();
        String nameVer = acc.getLstUser().get(0).getName();
        String passVer = acc.getLstUser().get(0).getPass();
        
        if(name.equals(nameVer)&&pass.equals(passVer)){
            System.out.println("case 1");
            
            return 1;
        }
        else if(name.strip().equals("")||pass.strip().equals("")){
            System.out.println("case 0");
            return 0;//please enter username and pass
        }
        else if(!pass.equals(passVer)&&name.equals(nameVer)){
            System.out.println("case 3");
            return 2;//// password not correct
        }
        else{
            System.out.println("case 2");
            System.out.println(nameVer);
            System.out.println(passVer);
            return -1;///acount is not valid
        }
        
    }
    
}
