package minerva.anthony.simpletodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddItemFragment extends DialogFragment {

    public interface AddItemListener {
        void addItem(Item item);
    }

    public AddItemFragment(){}

    public static AddItemFragment newInstance(){
        AddItemFragment f = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", "Add Item");
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("TITLE");
        getDialog().setTitle(title);
        final EditText etItemDesc = view.findViewById(R.id.etItemDesc);
        etItemDesc.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        Button btnAddItem = view.findViewById(R.id.btnAddItem);
        final EditText etDate = view.findViewById(R.id.etDate);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Check Input Validity
                AddItemListener mListener = (AddItemListener) getActivity();
                Item i = new Item(etItemDesc.getText().toString(), etDate.getText().toString());
                mListener.addItem(i);
                dismiss();
            }
        });
    }
}
