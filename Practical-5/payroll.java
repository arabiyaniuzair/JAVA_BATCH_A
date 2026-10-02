abstract class Employee{
    String name;
    int id;
    Employee(String name,int id){
        this.name=name;
        this.id=id;
    }
    abstract double monthlySalary();
}
class Fulltime extends Employee{
    double fixeddsalary;
    Fulltime(String name,int id,double fixeddsalary){
        super(name,id);
        this.fixeddsalary=fixeddsalary;
    }
    @Override 
    double monthlySalary(){
        return fixeddsalary;
    }
}
class Parttime extends Employee{
    double hours;
    double rates;
    Parttime(String name,int id,double hours,double rates){
        super(name,id);
        this.hours=hours;
        this.rates=rates;
    }
    @Override 
    double monthlySalary(){
        return hours*rates;
    }
}
class Intern extends Employee{
    double stipend;
    Intern(String name,int id,double stipend){
        super(name,id);
        this.stipend=stipend;
    }
    @Override 
    double monthlySalary(){
        return stipend;
    }
}

public class payroll {
    public static void main(String[] args){
        Employee[] employees = {
            new Fulltime("Uzair", 101, 50000),
            new Parttime("Rahul", 102, 80, 300),
            new Intern("Aman", 103, 10000),
            new Fulltime("Jay", 104, 60000)
        };
        double total = 0;

        for (Employee employee : employees) {

            double salary = employee.monthlySalary();

            System.out.printf(
                "Name: %s, ID: %d, Salary: %.2f",
                employee.name, employee.id, salary
            );

            if (employee instanceof Intern) {
                System.out.print(" - Intern: Stipend based");
            }

            System.out.println();

            total += salary;
        }

        System.out.printf("Total Payroll: %.2f%n", total);
    
    }
}
