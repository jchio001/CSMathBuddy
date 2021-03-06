package jonathanchiou.csmathhelper.main.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.Utils.SPHelper;

/**
 * Created by jman0_000 on 1/9/2016.
 */
public class AlertDialogFragment extends android.support.v4.app.DialogFragment{

    @Bind(R.id.checkBox) CheckBox cb;
    boolean hitCancel;

    public AlertDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alertdialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        cb.setChecked(SPHelper.getCbState(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(825, 475);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (!hitCancel)
            SPHelper.saveCbState(getActivity(), cb.isChecked());
    }

    @OnClick(R.id.OK)
    public void onClick() {
        dismiss();
    }

    @OnClick(R.id.cancel)
    public void onCancel() {
        hitCancel = true;
        dismiss();
    }

}
