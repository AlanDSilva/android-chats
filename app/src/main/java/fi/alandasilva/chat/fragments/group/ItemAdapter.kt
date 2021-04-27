package fi.alandasilva.chat.fragments.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fi.alandasilva.chat.R
import fi.alandasilva.chat.databinding.ItemGroupBinding
import fi.alandasilva.chat.model.Group

class ItemAdapter(private val dataset: ArrayList<Group>, private val navController: NavController)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    private val TAG = "GroupFragmentItemAdapter"

    class ItemViewHolder(val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val group = dataset[position]
        holder.binding.categoryTextView.text = group.category
        holder.binding.titleTextView.text = group.name
        holder.binding.cardView.setOnClickListener {
            val action = GroupFragmentDirections.actionGroupFragmentToChatFragment(
                group.id,
                group.name
            )
            navController.navigate(action)
        }

        if(group.photoUrl != "http://somephoto.com"){
            val storage = Firebase.storage
            val httpsReference = storage.getReferenceFromUrl(dataset[position].photoUrl)
            Glide.with(holder.itemView.context)
                .load(httpsReference)
                .circleCrop()
                .into(holder.binding.imageView)
        }
    }

}