# 🌍 Risk Map Routing System
A graph-based Java program that models a simplified world map of 42 countries (grouped into 6 continents) and finds optimal paths between countries. Developed as part of SOFTENG281 to practice object-oriented design and graph algorithms.

---

## 🧭 Project Overview

The world map is represented as a Graph, where:

- Each country is a node

- Each connection (land or water) is an undirected edge

- Countries have fuel costs used to calculate the cost of a route

**The system supports:**

- Finding the shortest path between two countries (via Breadth-First Search)

- Calculating total fuel cost for a route

- Displaying various graph-based statistics

### ✈️ Example

Route from Congo → Brazil via North Africa:

**Shortest path:** Congo → North Africa → Brazil

Fuel cost: Only for North Africa (intermediate country)

---

## 💡 Learning Objectives

- Model and traverse a graph

- Apply core data structures: List, Set, Map, Queue

- Use BFS to compute shortest paths
