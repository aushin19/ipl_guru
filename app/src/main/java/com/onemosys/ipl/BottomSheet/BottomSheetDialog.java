package com.onemosys.ipl.BottomSheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.onemosys.ipl.Dialog.WaitingDialog;
import com.onemosys.ipl.Helper.CommaFormat;
import com.onemosys.ipl.Helper.UsersData;
import com.onemosys.ipl.MainActivity;
import com.onemosys.ipl.NetworkCalls.GetNotifications;
import com.onemosys.ipl.R;
import com.onemosys.ipl.Transaction.JoinTrade;

public class BottomSheetDialog {
    public static TextView withdraw_notice_TV;
    public static ProgressBar withdraw_progressbar;
    public static MaterialButton withdraw_BTN;
    public static ShimmerFrameLayout withdraw_notice_shimmer;
    public static WaitingDialog waitingDialog;
    public static com.google.android.material.bottomsheet.BottomSheetDialog dialog;
    public static com.google.android.material.bottomsheet.BottomSheetDialog InternetDialog = null;
    Context context;
    public static RecyclerView trans_RCV;
    public static ConstraintLayout no_trans_CL;
    int seconds = 60;
    String payMode = "UPI ID : ";

    public BottomSheetDialog(@NonNull Context context) {
        this.context = context;
        waitingDialog = new WaitingDialog(context);
    }

    public void ErrorBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_error, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.try_again_BTN);

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void LoginBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_login, view.findViewById(R.id.bottomSheetContainer));
        MaterialButton login_createBTN = dialogView.findViewById(R.id.login_createBTN);
        MaterialButton login_loginBTN = dialogView.findViewById(R.id.login_loginBTN);

        login_createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("openFragment", "signup");
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });
        login_loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("openFragment", "login");
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    /*public void AdBlockerBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_ad_blocker, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.try_again_BTN);

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }*/

    public void NoInternetBottomSheet(View view) {
        InternetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        InternetDialog.setCancelable(false);
        InternetDialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_no_internet, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.try_again_BTN);

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetDialog.dismiss();
            }
        });

        InternetDialog.setContentView(dialogView);
        InternetDialog.show();
    }

    /*public void TaskCompleteBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_task_complete, view.findViewById(R.id.bottomSheetContainer));
        TextView dismiss_BTN = dialogView.findViewById(R.id.dismiss_BTN);

        dismiss_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent init = new Intent(context, Home.class);
                context.startActivity(init);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void TaskFailedBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_task_failed, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.try_again_BTN);

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent init = new Intent(context, Home.class);
                context.startActivity(init);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void QuizCompleteBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.botttom_sheet_quiz_complete, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.dismiss_BTN);

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent init = new Intent(context, Home.class);
                context.startActivity(init);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void QuizFailedBottomSheet(View view, int score) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(false);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_quiz_failed, view.findViewById(R.id.bottomSheetContainer));
        TextView try_again_BTN = dialogView.findViewById(R.id.dismiss_BTN);
        TextView quiz_score = dialogView.findViewById(R.id.quiz_score);

        quiz_score.setText(score + "%");

        try_again_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent init = new Intent(context, Home.class);
                context.startActivity(init);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }*/

    public void TradeBottomSheet(View view, double trade_coins, long prizePool) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_confirm_trade, view.findViewById(R.id.bottomSheetContainer));
        TextView quiz_coins_TV = dialogView.findViewById(R.id.quiz_coins_TV);
        TextView quiz_net_profit_TV = dialogView.findViewById(R.id.quiz_net_profit_TV);
        TextView quiz_winning_amt_TV = dialogView.findViewById(R.id.quiz_winning_amt_TV);
        TextView quiz_entry_fee_TV = dialogView.findViewById(R.id.quiz_entry_fee_TV);
        MaterialButton quiz_join_BTN = dialogView.findViewById(R.id.quiz_join_BTN);

        quiz_coins_TV.setText(CommaFormat.coinsCommaFormat(UsersData.getCoins(context)));
        quiz_winning_amt_TV.setText(CommaFormat.coinsCommaFormat(prizePool));
        quiz_entry_fee_TV.setText(CommaFormat.coinsCommaFormat(trade_coins));
        quiz_net_profit_TV.setText(((int) (Math.random() * (200 - 150)) + 150) + "");


        quiz_join_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //withdrawCoinsAndWallet.withdrawQuizCoins(context, quizFeedModal.quiz_coins, bundle, quizFeedModal);
                JoinTrade.JoinTrade(context, (int) trade_coins);
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    /*public void CouponConfirmBottomSheet(View view, CouponModal couponModal) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_confirm_coupon_redeem, view.findViewById(R.id.bottomSheetContainer));
        TextView quiz_coins_TV = dialogView.findViewById(R.id.quiz_coins_TV);
        TextView quiz_net_profit_TV = dialogView.findViewById(R.id.quiz_net_profit_TV);
        TextView quiz_winning_amt_TV = dialogView.findViewById(R.id.quiz_winning_amt_TV);
        TextView quiz_entry_fee_TV = dialogView.findViewById(R.id.quiz_entry_fee_TV);
        MaterialButton quiz_join_BTN = dialogView.findViewById(R.id.quiz_join_BTN);
        ImageView money_IMG = dialogView.findViewById(R.id.imageView4);

        quiz_coins_TV.setText(CommaFormat.coinsCommaFormat(UsersData.getCoins(context)));

        if (couponModal.type.equals("paytm"))
            quiz_winning_amt_TV.setText("₹" + CommaFormat.walletCommaFormat(couponModal.value));
        else if (couponModal.type.equals("paypal"))
            quiz_winning_amt_TV.setText("$" + CommaFormat.walletCommaFormat(couponModal.value));
        else if(couponModal.type.equals("uc")){
            quiz_winning_amt_TV.setText(CommaFormat.walletCommaFormat(couponModal.value) + " UC");
            money_IMG.setVisibility(View.GONE);
        }
        else if(couponModal.type.equals("diamond")){
            quiz_winning_amt_TV.setText(CommaFormat.walletCommaFormat(couponModal.value) + " Diamonds");
            money_IMG.setVisibility(View.GONE);
        }

        quiz_entry_fee_TV.setText(CommaFormat.coinsCommaFormat(couponModal.cost));
        quiz_net_profit_TV.setText(((int) (Math.random() * (200 - 150)) + 150) + "");

        quiz_join_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isUserExists.isUserExists(context)) {
                    waitingDialog.show();
                    if (couponModal.img == R.drawable.ic_paytm)
                        withdrawCoinsAndWallet.withdrawCoupons(context, couponModal.cost, "₹" + couponModal.value);
                    else
                        withdrawCoinsAndWallet.withdrawCoupons(context, couponModal.cost, "$" + couponModal.value);
                } else {
                    new BottomSheetDialog(context).LoginBottomSheet(((Activity) context).getWindow().getDecorView());
                }
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }*/

    /*public void RecoverPasswordBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_recover_password, view.findViewById(R.id.bottomSheetContainer));
        TextView recover_password_BTN = dialogView.findViewById(R.id.recover_password_BTN);
        TextInputEditText email_TB = dialogView.findViewById(R.id.email_TB);

        recover_password_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingDialog.show();
                dialog.dismiss();
                verifyAndRecover.recoverEmail(context, email_TB.getText().toString().trim());
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }*/

    /*public void TransactionHistoryBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_transaction_history, view.findViewById(R.id.bottomSheetContainer));

        trans_RCV = dialogView.findViewById(R.id.trans_RCV);
        no_trans_CL = dialogView.findViewById(R.id.no_trans_CL);
        trans_RCV.setLayoutManager(new LinearLayoutManager(context));

        new Thread(new Runnable() {
            @Override
            public void run() {
                new GetTransactionFeed(context).execute();
            }
        }).start();

        dialog.setContentView(dialogView);
        dialog.show();
    }*/

    public void notificationBottomSheet(View view) {
        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.notifications_layout, view.findViewById(R.id.bottomSheetContainer));
        RecyclerView notification_RCV = dialogView.findViewById(R.id.notification_RCV);
        notification_RCV.setLayoutManager(new LinearLayoutManager(context));

        new Thread(new Runnable() {
            @Override
            public void run() {
                new GetNotifications(context, notification_RCV).execute();
            }
        }).start();

        dialogView.findViewById(R.id.notification_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
    }

    /*public void OTPVerificationBottomSheet(View view, double wallet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                verifyAndRecover.sendOTP(context);
            }
        }).start();

        dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(context, R.style.BottomSheetTheme);
        dialog.setCancelable(true);
        dialog.setDismissWithAnimation(true);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_otp_verification, view.findViewById(R.id.bottomSheetContainer));
        MaterialButton otp_verify_BTN = dialogView.findViewById(R.id.otp_verify_BTN);
        TextView otp_resend_TV = dialogView.findViewById(R.id.otp_resend_TV);

        EditText OTP_Text1, OTP_Text2, OTP_Text3, OTP_Text4;
        OTP_Text1 = dialogView.findViewById(R.id.OTP_Text1);
        OTP_Text2 = dialogView.findViewById(R.id.OTP_Text2);
        OTP_Text3 = dialogView.findViewById(R.id.OTP_Text3);
        OTP_Text4 = dialogView.findViewById(R.id.OTP_Text4);

        OTP_Text1.requestFocus();
        OTP_Text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OTP_Text2.requestFocus();
            }
        });
        OTP_Text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OTP_Text3.requestFocus();
            }
        });
        OTP_Text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OTP_Text4.requestFocus();
            }
        });
        OTP_Text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (OTP_Text4.getText().toString().length() != 0) {
                    OTP_Text4.clearFocus();
                }
            }
        });

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                otp_resend_TV.setText(seconds + "s");
                seconds--;
            }

            public void onFinish() {
                otp_resend_TV.setText("RESEND OTP");
                seconds = 60;
            }
        };

        countDownTimer.start();

        otp_resend_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_resend_TV.getText().toString().equals("RESEND OTP")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            verifyAndRecover.sendOTP(context);
                        }
                    }).start();
                    countDownTimer.start();
                    Toast.makeText(context, "Check Your SPAM FOLDER for OTP", Toast.LENGTH_LONG).show();
                }
            }
        });

        otp_verify_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = OTP_Text1.getText().toString().trim() + OTP_Text2.getText().toString().trim() + OTP_Text3.getText().toString().trim() + OTP_Text4.getText().toString().trim();

                if (otp.equals(verifyAndRecover.OTP)) {
                    waitingDialog.show();
                    dialog.dismiss();
                    verifyAndRecover.OTPVerified(context, wallet);
                } else {
                    showSnackBar.build("Wrong OTP Entered!", R.color.light_red, context, dialog.getWindow().getDecorView());
                }
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    private static boolean checkWalletID(String text) {
        String emailRegex = "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}";
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(text).matches()) {
            return true;
        }
        return false;
    }*/

}
