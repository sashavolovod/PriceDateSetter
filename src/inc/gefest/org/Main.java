/*
 * Created by Sasha Volovod on 30.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package inc.gefest.org;

public class Main {

    public static void main(String[] args) {

        Person p = new Person("Ivanov", 23);
        System.out.printf(p.toString());
    }
}