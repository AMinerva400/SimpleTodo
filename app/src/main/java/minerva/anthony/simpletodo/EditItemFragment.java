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

public class EditItemFragment extends DialogFragment {

    public interface EditItemListener{
        void editItem(Item item, int position);
    }

    public EditItemFragment(){}

    public static EditItemFragment newInstance(Item i, int position){
        EditItemFragment f = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", "Edit Item");
        args.putString("DESC", i.itemDescription);
        args.putString("DATE", i.dueDate);
        args.putInt("POS", position);
        args.putLong("ID", i.id);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("TITLE");
        getDialog().setTitle(title);
        final EditText etDate = view.findViewById(R.id.etDate);
        etDate.setText(getArguments().getString("DATE"));
        final EditText etItemDesc = view.findViewById(R.id.etItemDesc);
        etItemDesc.setText(getArguments().getString("DESC"));
        etItemDesc.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        Button btnEditItem = view.findViewById(R.id.btnSave);
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemListener mListener = (EditItemListener) getActivity();
                Item i = new Item(etItemDesc.getText().toString(), etDate.getText().toString());
                i.id = getArguments().getLong("ID");
                mListener.editItem(i, getArguments().getInt("POS"));
                dismiss();
            }
        });
    }
}
