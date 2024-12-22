import requests
from bs4 import BeautifulSoup
import json
import re
import time

def clean_text(text):
    # Remove extra whitespace and newlines
    return ' '.join(text.strip().split())

def extract_name_info(row):
    try:
        # Extract link and name
        link_cell = row.find('td')
        if not link_cell:
            return None

        link = link_cell.find('a')
        if not link:
            return None

        name = link.text.strip()

        # Extract description
        desc_cell = row.find_all('td')[1]
        if not desc_cell:
            return None

        desc_text = desc_cell.text.strip()

        # Extract origin, meaning and name days using regex
        origin_match = re.search(r'Pochodzenie:\s*([^*]+)', desc_text)
        meaning_match = re.search(r'Znaczenie:\s*([^*]+)', desc_text)
        namedays_match = re.search(r'Imieniny:\s*([^*]+)', desc_text)

        origin = origin_match.group(1).strip() if origin_match else ""
        meaning = meaning_match.group(1).strip() if meaning_match else ""
        namedays = namedays_match.group(1).strip() if namedays_match else ""

        return {
            "name": name,
            "origin": origin,
            "meaning": meaning,
            "namedays": namedays
        }
    except Exception as e:
        print(f"Error processing row: {e}")
        return None

def scrape_names():
    url = "https://dzidziusiowo.pl/baza-imion"

    try:
        # Add headers to mimic a browser
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
        }

        # Make request to the website
        print("Making request to the website...")
        response = requests.get(url, headers=headers)
        response.raise_for_status()

        # Print response status and length
        print(f"Response status: {response.status_code}")
        print(f"Response length: {len(response.text)}")

        # Parse HTML
        soup = BeautifulSoup(response.text, 'html.parser')

        # Find the table with names
        tables = soup.find_all('table')
        print(f"Found {len(tables)} tables on the page")

        # Try to find the correct table
        table = None
        for t in tables:
            # Look for table with name-related content
            if t.find('td') and any('imię' in str(td).lower() or 'pochodzenie' in str(td).lower() for td in t.find_all('td')):
                table = t
                break

        if not table:
            print("HTML content:")
            print(response.text[:1000])  # Print first 1000 characters for debugging
            raise Exception("Names table not found")

        # Extract rows
        rows = table.find_all('tr')
        print(f"Found {len(rows)} rows in the table")

        # Process each row and store name information
        names_data = []
        for row in rows:
            name_info = extract_name_info(row)
            if name_info:
                names_data.append(name_info)
                print(f"Processed name: {name_info['name']}")

        # Save to JSON file
        with open('polish_names.json', 'w', encoding='utf-8') as f:
            json.dump(names_data, f, ensure_ascii=False, indent=2)

        print(f"Successfully scraped {len(names_data)} names")

    except requests.exceptions.RequestException as e:
        print(f"Error making request: {e}")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    getAllPolishNames.scrape_names()
