package com.ben.drivenbluetooth.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ben.drivenbluetooth.Global;
import com.ben.drivenbluetooth.MainActivity;
import com.ben.drivenbluetooth.R;

public final class DrivenSettings {

	private DrivenSettings() {
		// required empty constructor
	}

	public static void InitializeSettings() {
		PreferenceManager.setDefaultValues(MainActivity.getAppContext(), R.xml.user_settings, false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());

        WheelTeeth(prefs);
        MotorTeeth(prefs);
		Mode(prefs);
		Location(prefs);
		Units(prefs);
		BTDevice(prefs);
		CarName(prefs);
		Graphs(prefs);
        UDP(prefs);
	}

	public static void QuickChangeMode() {
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
			int mode = Integer.parseInt(prefs.getString("prefMode", "0"));

			mode = mode == 0 ? 1 : 0; // flip it

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("prefMode", Integer.toString(mode));
			editor.apply();

			Global.Mode = Global.MODE.values()[mode];
			MainActivity.myMode.setText(Global.MODE.values()[mode].name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private static void MotorTeeth(SharedPreferences prefs) {
        try {
            Global.MotorTeeth = parseMotorTeeth(prefs.getString("prefMotorTeeth", ""));
        } catch (Exception e) {
            // probably not needed
        }
    }

    private static void WheelTeeth(SharedPreferences prefs) {
        try {
            Global.WheelTeeth = parseWheelTeeth(prefs.getString("prefWheelTeeth", ""));
        } catch (Exception e) {
            // probably not needed
        }
    }

	private static void Mode(SharedPreferences prefs) {
		try {
			int mode = Integer.valueOf(prefs.getString("prefMode", "0"));
			Global.Mode = Global.MODE.values()[mode];
			MainActivity.myMode.setText(Global.MODE.values()[mode].name());
		} catch (Exception e) {
			Global.Mode = Global.MODE.DEMO;
			MainActivity.myMode.setText("DEMO");
		}
	}

	private static void Units(SharedPreferences prefs) {
		try {
			int units = Integer.valueOf(prefs.getString("prefSpeedUnits", "0"));
			Global.Unit = Global.UNIT.values()[units];
		} catch (Exception e) {
			Global.Unit = Global.UNIT.MPH;
		}
	}

	private static void Location(SharedPreferences prefs) {
		try {
			int location = Integer.valueOf(prefs.getString("prefLocation", "0"));
			Global.LocationStatus = Global.LOCATION.values()[location];
		} catch (Exception e) {
			Global.LocationStatus = Global.LOCATION.DISABLED;
		}
	}

	private static void BTDevice(SharedPreferences prefs) {
		try {
			Global.BTDeviceName = prefs.getString("prefBTDeviceName", "");
		} catch (Exception e) {
			// probably not needed
		}
	}

	private static void CarName(SharedPreferences prefs) {
		try {
			Global.CarName = prefs.getString("prefCarName", "");
		} catch (Exception e) {
			// probably not needed
		}
	}

	private static void Graphs(SharedPreferences prefs) {
		try {
			Global.EnableGraphs = Integer.valueOf(prefs.getString("prefGraphs", "")) != 0;
		} catch (Exception e) {
			// probably not needed
		}
	}

    private static void UDP(SharedPreferences prefs) {
        Global.UDPPassword = prefs.getString("prefUDP", "");
        Global.UDPEnabled = true;
    }

    public static int[] parseWheelTeeth(String wheelTeethString) {
        wheelTeethString = wheelTeethString.replaceAll("\\s+", ""); // remove spaces
        String[] wheelTeethStringArray = wheelTeethString.split(",");

        int[] wheelTeeth = new int[wheelTeethStringArray.length];

        for (int i = 0; i < wheelTeethStringArray.length; i++) {
            wheelTeeth[i] = Integer.parseInt(wheelTeethStringArray[i]);
        }

        return wheelTeeth;
    }

    public static int parseMotorTeeth(String motorTeethString) {
        motorTeethString = motorTeethString.replaceAll("\\s+", ""); // remove spaces
        return Integer.parseInt(motorTeethString);
    }
}