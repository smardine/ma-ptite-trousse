package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * @author smardine
 */
public class CountdownWidget extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {

		// called when widgets are deleted

		// see that you get an array of widgetIds which are deleted

		// so handle the delete of multiple widgets in an iteration

		super.onDeleted(context, appWidgetIds);

	}

	@Override
	public void onDisabled(Context context) {

		super.onDisabled(context);

		// runs when all of the instances of the widget are deleted from

		// the home screen

		// here you can do some setup

	}

	@Override
	public void onEnabled(Context context) {

		super.onEnabled(context);

		// runs when all of the first instance of the widget are placed

		// on the home screen

	}

	@Override
	public void onReceive(Context context, Intent intent) {

		// all the intents get handled by this method

		// mainly used to handle self created intents, which are not

		// handled by any other method

		// the super call delegates the action to the other methods

		// for example the APPWIDGET_UPDATE intent arrives here first

		// and the super call executes the onUpdate in this case

		// so it is even possible to handle the functionality of the

		// other methods here

		// or if you don't call super you can overwrite the standard

		// flow of intent handling

		super.onReceive(context, intent);
		// String action = intent.getAction();

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,

	int[] appWidgetIds) {

		// runs on APPWIDGET_UPDATE

		// here is the widget content set, and updated

		// it is called once when the widget created

		// and periodically as set in the metadata xml

		// the layout modifications can be done using the AppWidgetManager

		// passed in the parameter, we will discuss it later

		// the appWidgetIds contains the Ids of all the widget instances

		// so here you want likely update all of them in an iteration

		// we will use only the first creation run

		// mise a jour des produits perime dans la base:
		// for (int appWidgetid : appWidgetIds) {
		@SuppressWarnings("unused")
		majWidget maj = new majWidget(context, true);
		// }

	}

}
