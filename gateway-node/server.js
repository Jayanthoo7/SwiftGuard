require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const app = express();
app.use(express.json());
app.use(cors()); // Allows our frontend to fetch data

mongoose.connect(process.env.MONGO_URI || 'mongodb://localhost:27017/swiftguard')
  .then(() => console.log('MongoDB Connected for Gateway'))
  .catch(err => console.log('MongoDB Connection Error:', err));

const TransactionSchema = new mongoose.Schema({
    userId: String,
    amount: Number,
    location: String,
    status: { type: String, default: 'PENDING' }, 
    riskScore: { type: Number, default: 0 }
});

const Transaction = mongoose.model('Transaction', TransactionSchema);

// Endpoint to RECEIVE transactions
app.post('/api/transactions', async (req, res) => {
    try {
        const newTx = await Transaction.create(req.body);
        res.status(202).json({ message: "Queued", transactionId: newTx._id });
    } catch (error) {
        res.status(500).json({ error: "Failed to process" });
    }
});

// NEW Endpoint to READ transactions for the dashboard
app.get('/api/transactions', async (req, res) => {
    try {
        const transactions = await Transaction.find().sort({ _id: -1 });
        res.status(200).json(transactions);
    } catch (error) {
        res.status(500).json({ error: "Failed to fetch" });
    }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Gateway running on port ${PORT}`));
