import Search from "views/Search/Search.jsx";
import Profile from "views/Profile/Profile.jsx";
import Login from "views/Login/Login.jsx";
import Contact from "views/Contact/Contact.jsx";
import Register from "views/Register/Register.jsx";
import HotelsPage from "../views/HotelsPage/HotelsPage";
import HotelDetails from "../views/Room/RoomsPage";
import BookRoom from "../views/Book/BookingPage";
import ReservationDetails from "../views/ReservationDetails/ReservationDetails";

var indexRoutes = [
  {path: '/profile', name: "Profile", component: Profile},
  {path: '/login', name: "Login", component: Login},
  {path: '/contact', name: "Contact", component: Contact},
  {path: '/register', name: "Register", component: Register},
  {path: '/hotels', name: "HotelsPage", component: HotelsPage},
  {path: '/hotel/:id', name: 'Hotel Details', component: HotelDetails},
  {path: '/book/:bookingCode', name: 'Book New Reservation', component: BookRoom},
  {path: '/details', name: 'Reservation Details', component: ReservationDetails},
  {path: "/", name: "Search", component: Search}
];

export default indexRoutes;