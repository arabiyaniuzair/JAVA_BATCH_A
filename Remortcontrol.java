interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
        off();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan is ON");
    }

    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

@FunctionalInterface
interface SwitchDecision {
    boolean maySwitchOn(Switchable device, int hour);
}

public class Remortcontrol {
    public static void main(String[] args) {
        Switchable[] devices = {new Fan(), new Light()};

        for (Switchable device : devices) {
            device.toggle();
        }

        SwitchDecision anonymous = new SwitchDecision() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        SwitchDecision lambda = (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Anonymous class: " +
                anonymous.maySwitchOn(devices[0], 10));

        System.out.println("Lambda: " +
                lambda.maySwitchOn(devices[1], 23));
    }
}