SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `duties`
--

CREATE TABLE `duties` (
  `duty_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `value` varchar(255) NOT NULL,
  `duty_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dutiesCompletions`
--

CREATE TABLE `dutiesCompletions` (
  `duty_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  `duty_name` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `value` varchar(255) NOT NULL,
  `completion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `flats`
--

CREATE TABLE `flats` (
  `flat_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `invitation_code` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages`
--

CREATE TABLE `messages` (
  `message_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message_text` text CHARACTER SET utf16 COLLATE utf16_polish_ci NOT NULL,
  `message_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages_participant`
--

CREATE TABLE `messages_participant` (
  `messages_participant_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `read_date` datetime DEFAULT NULL,
  `isDeleted` tinyint(4) DEFAULT NULL,
  `message_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rent_history`
--

CREATE TABLE `rent_history` (
  `rent_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  `total_value` float NOT NULL,
  `per_person` float(7,2) NOT NULL,
  `rent_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rent_item`
--

CREATE TABLE `rent_item` (
  `rent_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `value` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shopping`
--

CREATE TABLE `shopping` (
  `item_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `price` float(7,2) NOT NULL,
  `purchase_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shopping_split`
--

CREATE TABLE `shopping_split` (
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `flat_id` int(11) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `balance` float DEFAULT '0',
  `points` float DEFAULT '0',
  `token` varchar(255) NOT NULL DEFAULT 'first',
  `firebase_token` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `duties`
--
ALTER TABLE `duties`
  ADD PRIMARY KEY (`duty_id`);

--
-- Indexes for table `flats`
--
ALTER TABLE `flats`
  ADD PRIMARY KEY (`flat_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `messages_participant`
--
ALTER TABLE `messages_participant`
  ADD PRIMARY KEY (`messages_participant_id`);

--
-- Indexes for table `rent_history`
--
ALTER TABLE `rent_history`
  ADD PRIMARY KEY (`rent_id`);

--
-- Indexes for table `shopping`
--
ALTER TABLE `shopping`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `duties`
--
ALTER TABLE `duties`
  MODIFY `duty_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `flats`
--
ALTER TABLE `flats`
  MODIFY `flat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `messages`
--
ALTER TABLE `messages`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `messages_participant`
--
ALTER TABLE `messages_participant`
  MODIFY `messages_participant_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `rent_history`
--
ALTER TABLE `rent_history`
  MODIFY `rent_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `shopping`
--
ALTER TABLE `shopping`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
