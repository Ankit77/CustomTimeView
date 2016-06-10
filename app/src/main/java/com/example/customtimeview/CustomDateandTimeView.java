package com.example.customtimeview;

//**

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/* Created By S.B. on 26/04/2016

        * Creates One Custom TextView with its custom Border and with DatePicker and Time Picker which we are going to use in future with Choice what we want
        */
public class CustomDateandTimeView extends TextView implements View.OnClickListener {
    public String dateFormat = "dd/MM/yyyy";// From attrs.xml file with string format
    public String hourFormat = "HH:MM";
    public String timeFormat_12 = "hh:mm a";
    public String timeFormat_24 = "HH:mm";
    public int timeFormat = 12;// From attrs.xml file with integer format
    public Integer type = 1;// From attrs.xml file with integer format
    public boolean showBorder; // From attrs.xml file with boolean format
    private Paint paint = new Paint();
    private int borderColor = Color.BLACK;// From attrs file with color format
    private int borderRadius = 2;// From atts file with dimension format
    private Context mContext;
    private Calendar mCalendar = Calendar.getInstance();
    private DatePickerDialog datepickerdialog;
    private TimePickerDialog timepickerdialog;
    private String datetimetext = "";

    private ArrayList<String> dateFormateList = new ArrayList<>();
    private ArrayList<Integer> timeFormateList = new ArrayList<>();
    private ArrayList<Integer> typeFormateList = new ArrayList<>();
    private dateTimeSelecter dateTimeSelecter;


    public void setDateTimeSelecter(CustomDateandTimeView.dateTimeSelecter dateTimeSelecter) {
        this.dateTimeSelecter = dateTimeSelecter;
    }

    //Date Picker lister set value to the date
    private TimePickerDialog.OnTimeSetListener starttimePickerListener = new TimePickerDialog.OnTimeSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            /**
             * Define Below condition because if we want to show the 24 hour and 12 hour format in time picker then it is define easily with that timeformat
             */
            //setText("");
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);

            if (timeFormat == 12) {
                if (type == 3) {

                    if (TextUtils.isEmpty(getText())) {
                        setText(new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime()));

                    } else {

                        setText(getText() + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime()));
                    }
                } else if (type == 2) {

                    if (TextUtils.isEmpty(getText())) {
                        setText(new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime()));

                    } else {

                        setText(new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime()));
                    }
                }

            } else if (timeFormat == 24) {
                if (type == 3) {

                    if (TextUtils.isEmpty(getText())) {
                        setText(new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime()));

                    } else {

                        setText(getText() + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime()));
                    }
                } else if (type == 2) {

                    if (TextUtils.isEmpty(getText())) {
                        setText(new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime()));

                    } else {

                        setText(new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime()));
                    }
                }

            }
            if (dateTimeSelecter != null) {
                dateTimeSelecter.onSelect();
            }

        }
    };


    //Date Picker lister set value to the date
    private DatePickerDialog.OnDateSetListener startdatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mCalendar.set(year, month, day);
            setText(new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()));

            if (type == 3) {
                datetimetext = "";
                datetimetext = new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime());
                timePickerDialog();
            }

            if (type != 3) {
                if (dateTimeSelecter != null) {
                    dateTimeSelecter.onSelect();
                }
            }

        }


    };


    /***
     * Sets the default value to the date view constructors
     *
     * @param context this Context is passing for Constructor call.
     */
    public CustomDateandTimeView(Context context) {
        super(context);
        this.mContext = context;
        setDate();
    }


    /**
     * sets the attribute to the local variable that are defined in xml files
     *
     * @param context an activity mContext object
     * @param attrs   set of attribute which contain above define parameters
     */
    public CustomDateandTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomDateandTimeView);
        borderColor = a.getColor(R.styleable.CustomDateandTimeView_border_color, Color.BLACK);
        showBorder = a.getBoolean(R.styleable.CustomDateandTimeView_show_border, true);
        borderRadius = a.getDimensionPixelSize(R.styleable.CustomDateandTimeView_border_radius, 2);
        timeFormat = a.getInt(R.styleable.CustomDateandTimeView_timeformat, 12);
        type = a.getInt(R.styleable.CustomDateandTimeView_type, 0);

        /**
         * @param attrs type is an select the date picker and time picker
         *              type= 1 then its shown only Date and datepickerdialog
         *              type = 2 then its shown only Time and timepickerdialog
         *              type= 3 then its shown both TimePicker and DatePicker Dialog with text
         */
        if (showBorder) {
            init();
        }
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.CustomDateandTimeView_dateformat) {
                dateFormat = checkDateFormat(a.getString(attr));
            } else if (attr == R.styleable.CustomDateandTimeView_timeformat) {

                timeFormat = checkTimeFormat(a.getInt(attr, 12));
            } else if (attr == R.styleable.CustomDateandTimeView_show_border) {
                showBorder = a.getBoolean(attr, true);
            } else if (attr == R.styleable.CustomDateandTimeView_border_color) {
                borderColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.CustomDateandTimeView_border_radius) {
                borderRadius = a.getDimensionPixelSize(attr, 2);
            } else if (attr == R.styleable.CustomDateandTimeView_type) {
                type = checkTypeFormat(a.getInt(attr, 0));
            }
        }


        if (type == 1) {
            setDate();
        } else if (type == 2) {
            setTime();
        } else if (type == 3) {
            setDateAndTime();
        } else {
            setDate();
        }
        a.recycle();
    }


    /**
     * sets the attribute to the local variable that are defined in xml files
     *
     * @param context an activity mContext object
     * @param attrs   set of attribute which contain above define parameters
     */
    public CustomDateandTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setDate();
        init();
    }

    /**
     * Create init() method for Set the Custom Border
     */
    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderRadius);

    }

    /**
     * Draws border on canvas
     *
     * @param canvas on which border will be created
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBorder) {
            for (int i = 0; i < 4; i++) {
                if (i == 0) {//Top
                    canvas.drawLine(0, 0, getWidth(), 0, paint);
                } else if (i == 1) {
                    canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
                } else if (i == 2) {
                    canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
                } else if (i == 3) {
                    canvas.drawLine(0, 0, 0, getHeight(), paint);
                }
            }
        }
    }

    /**
     * Create setDate method for SetText the Today's date
     */
    private void setDate() {

//        if (showDefaultDate) {
//            SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
//            String today = sdf.format(mCalendar.getTime());
//            setText(today);  // self = DateView = subclass of TextView
//        }
        setText(showTimeString());
        this.setOnClickListener(this);
    }

    /**
     * Create setTime  method for the Today's ShowTime
     */
    public void setTime() {

        setText(showTimeString());
        this.setOnClickListener(this);

    }

    public void setDateAndTime() {
        setText(showTimeString());
        this.setOnClickListener(this);

    }

    /**
     * Create showTime  method for SetText the Today's time
     */
    public void showTime(int hour, int minute) {

        setText(showTimeString());
    }

    private String showTimeString() {

        String date = "";

        if (timeFormat == 12) {
            if (type == 1) {

                date = new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime());

            } else if (type == 2) {

                date = new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime());

            } else if (type == 3) {

                date = new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_12, Locale.US).format(mCalendar.getTime());
            }
        } else if (timeFormat == 24) {


            if (type == 1) {

                date = new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime());

            } else if (type == 2) {


                date = new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime());

            } else if (type == 3) {
                date = new SimpleDateFormat(CustomDateandTimeView.this.dateFormat, Locale.US).format(mCalendar.getTime()) + " " + new SimpleDateFormat(CustomDateandTimeView.this.timeFormat_24, Locale.US).format(mCalendar.getTime());
            }
        }

        return date;
    }

    public void datePickerDialog() {
        if (datepickerdialog == null) {
            final int year = mCalendar.get(Calendar.YEAR);
            final int month = mCalendar.get(Calendar.MONTH);
            final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            datepickerdialog = new DatePickerDialog(mContext, startdatePickerListener, year, month, day);
            datepickerdialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
            datepickerdialog.setTitle("Set Calender Date");
        }
        datepickerdialog.show();
        datepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (type == 3) {
                    showTimeString();

                }
            }
        });
    }

    public void timePickerDialog() {
        if (timepickerdialog == null) {
            if (timeFormat == 12) {
                timepickerdialog = new TimePickerDialog(mContext, starttimePickerListener,
                        Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE), false);

                timepickerdialog.setTitle("Set hours and minutes");
            } else if (timeFormat == 24) {
                timepickerdialog = new TimePickerDialog(mContext, starttimePickerListener,
                        Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE), true);

                timepickerdialog.setTitle("Set hours and minutes");
            }
        }

        timepickerdialog.show();
        timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("sdss", "sdsd");
                setText(showTimeString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        /***
         *          type= 1 then its shown only Date and datepickerdialog
         *              type = 2 then its shown only Time and timepickerdialog
         *              type= 3 then its shown both TimePicker and DatePicker Dialog both..but for maintain sequence we take datepickerdialog first and timepickerdialog called after datepickerdialog close
         */
        if (type == 1) {
            datePickerDialog();
        } else if (type == 2) {
            timePickerDialog();
        } else if (type == 3) {
            datePickerDialog();
        }


    }

    private String checkDateFormat(String format) {

        dateFormateList.add("dd/MM/yyyy");
        dateFormateList.add("dd/MMM/yyyy");
        dateFormateList.add("dd-MM-yyyy");
        if (dateFormateList.contains(format)) {
            return format;
        } else {
            return "dd/MM/yyyy";
        }

    }

    private Integer checkTimeFormat(Integer format) {
        timeFormateList.add(12);
        timeFormateList.add(24);
        if (timeFormateList.contains(format)) {
            return format;
        } else {
            return 12;
        }
    }

    private Integer checkTypeFormat(Integer format) {
        typeFormateList.add(1);
        typeFormateList.add(2);
        typeFormateList.add(3);
        if (typeFormateList.contains(format)) {
            return format;
        } else {
            return 1;
        }
    }

    public interface dateTimeSelecter {
        public void onSelect();

    }
}


